package io.shortcut.showcase.presentation.home.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.shortcut.showcase.data.mapper.Country
import io.shortcut.showcase.domain.repository.HomeScreenRepository
import io.shortcut.showcase.presentation.common.filter.data.FilterButtonData
import io.shortcut.showcase.presentation.data.ShowcaseAppUI
import io.shortcut.showcase.presentation.home.data.CategorySection
import io.shortcut.showcase.util.resource.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeScreenRepository
) : ViewModel() {

    private val _homeViewStateFlow = MutableStateFlow(HomeViewState())
    val homeViewState = _homeViewStateFlow.asStateFlow()

    private val _viewEffects = MutableSharedFlow<HomeViewEffect>()
    val viewEffects = _viewEffects.asSharedFlow()

    init {
        fetchAppsDataFromRemote()
        fetchBanners()
        genFilterButtons()
    }

    private fun sendViewEffect(effect: HomeViewEffect) {
        viewModelScope.launch {
            _viewEffects.emit(effect)
        }
    }

    private fun fetchBanners() {
        viewModelScope.launch {
            repository.fetchBanners().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { banners ->
                            _homeViewStateFlow.update {
                                it.copy(
                                    banners = banners,
                                    refreshing = false
                                )
                            }
                        }
                    }

                    is Resource.Loading -> {
                        _homeViewStateFlow.update {
                            it.copy(refreshing = result.isLoading)
                        }
                    }

                    is Resource.Error -> {
                        /*TODO*/
                    }
                }
            }
        }
    }

    fun fetchAppsDataFromRemote() {
        viewModelScope.launch {
            repository.fetchAppsFromRemote(homeViewState.value.activeCountryFilter)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            setUpHomeViewState(result)
                        }

                        is Resource.Loading -> {
                            _homeViewStateFlow.update {
                                it.copy(refreshing = result.isLoading)
                            }
                        }

                        is Resource.Error -> {
                            /* TODO */
                        }
                    }
                }
        }
    }

    private fun fetchDataFromDatabase(country: Country) {
        viewModelScope.launch {
            repository.fetchAppsFromDatabase(country)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            setUpHomeViewState(result)
                        }

                        is Resource.Loading -> {
                            _homeViewStateFlow.update {
                                it.copy(loading = result.isLoading)
                            }
                        }

                        is Resource.Error -> {
                            /* TODO */
                        }
                    }
                }
        }
    }

    private fun setUpHomeViewState(result: Resource<List<ShowcaseAppUI>>) {
        result.data?.let { apps ->
            // Attach on click listener
            val categorizedApps = apps.groupBy { it.generalCategory }
                .map {
                    CategorySection(
                        generalCategory = it.key,
                        apps = it.value.map { app ->
                            app.copy(
                                onClick = { clickToOpenBottomSheet(app) }
                            )
                        },
                        onClickShowAll = {}
                    )
                }

            _homeViewStateFlow.update { state ->
                state.copy(categorizedApps = categorizedApps, refreshing = false)
            }
        }
    }

    private fun clickToOpenBottomSheet(app: ShowcaseAppUI) {
        _homeViewStateFlow.update {
            it.copy(appInView = app, refreshing = false)
        }
        sendViewEffect(HomeViewEffect.OpenBottomSheet)
    }

    private fun genFilterButtons(activeFilter: Country = homeViewState.value.activeCountryFilter) {
        val countryFilter = Country.values()
        _homeViewStateFlow.update {
            it.copy(
                filterButtons = countryFilter.mapNotNull { country ->
                    if (country != Country.Unknown) {
                        FilterButtonData(
                            type = country,
                            selected = activeFilter == country,
                            onClick = { setCountryFilter(country) }
                        )
                    } else {
                        null
                    }
                }
            )
        }
    }

    private fun setCountryFilter(country: Country) {
        viewModelScope.launch {
            _homeViewStateFlow.update { state ->
                state.copy(
                    activeCountryFilter = country
                )
            }
            genFilterButtons(country)
            fetchDataFromDatabase(country)
        }
    }
}