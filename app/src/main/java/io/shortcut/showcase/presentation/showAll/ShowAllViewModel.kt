package io.shortcut.showcase.presentation.showAll

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.shortcut.showcase.data.mapper.Country
import io.shortcut.showcase.data.mapper.GeneralCategory
import io.shortcut.showcase.domain.repository.AppsRepository
import io.shortcut.showcase.presentation.common.filter.data.CountryFilter
import io.shortcut.showcase.presentation.data.ShowcaseAppUI
import io.shortcut.showcase.util.resource.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowAllViewModel @Inject constructor(
    private val appRepository: AppsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _showAppListSState: MutableStateFlow<ShowAllAppsState> =
        MutableStateFlow(ShowAllAppsState())
    val showAppListSState = _showAppListSState.asStateFlow()

    private val _viewEffects = MutableSharedFlow<ShowAllAppEvent>()
    val viewEffects = _viewEffects.asSharedFlow()

    private val initialCountrySelected = savedStateHandle.get<String>("country")?.let {
        enumValueOf(it)
    } ?: Country.All
    private val initialCategorySelected = savedStateHandle.get<String>("category")?.let {
        enumValueOf(it)
    } ?: GeneralCategory.BUSINESS

    init {
        _showAppListSState.update { state ->
            state.copy(
                countryFilter = CountryFilter.getCountryFilterList(initialCountrySelected) {
                    setCountryFilter(it)
                },
                selectedCategory = initialCategorySelected
            )
        }
        fetchAppsList(initialCountrySelected, initialCategorySelected)
    }

    private fun setCountryFilter(countrySelected: Country) {
        fetchAppsList(countrySelected, initialCategorySelected)
    }

    private fun fetchAppsList(countrySelected: Country, categorySelected: GeneralCategory) {
        viewModelScope.launch {
            appRepository.fetchAppsFromDatabase(
                countrySelected,
                selectedCategory = categorySelected
            ).collect { result ->
                when (result) {
                    is Resource.Error -> {}
                    is Resource.Loading -> {
                        _showAppListSState.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { apps ->
                            _showAppListSState.update { state ->
                                state.copy(
                                    apps = apps.map { app ->
                                        app.copy(
                                            onClick = { clickToOpenBottomSheet(app) }
                                        )
                                    },
                                    countryFilter = CountryFilter.getCountryFilterList(
                                        countrySelected
                                    ) {
                                        setCountryFilter(it)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun sendViewEffect(effect: ShowAllAppEvent) {
        viewModelScope.launch {
            _viewEffects.emit(effect)
        }
    }

    private fun clickToOpenBottomSheet(app: ShowcaseAppUI) {
        _showAppListSState.update {
            it.copy(appSelectedForBottomSheet = app)
        }
        sendViewEffect(ShowAllAppEvent.ShowAppInformation(app))
    }

    fun dismissAppInformation() {
        sendViewEffect(ShowAllAppEvent.DismissAppInformation)
    }
}