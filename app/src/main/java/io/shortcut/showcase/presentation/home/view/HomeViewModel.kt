package io.shortcut.showcase.presentation.home.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.shortcut.showcase.data.mapper.Country
import io.shortcut.showcase.domain.repository.AppsRepository
import io.shortcut.showcase.domain.repository.HomeScreenRepository
import io.shortcut.showcase.presentation.common.filter.data.CountryFilter
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
    private val repository: HomeScreenRepository,
    private val appRepository: AppsRepository
) : ViewModel() {

    private val _homeViewStateFlow = MutableStateFlow(HomeViewState())
    val homeViewState = _homeViewStateFlow.asStateFlow()

    private val _viewEffects = MutableSharedFlow<HomeViewEffect>()
    val viewEffects = _viewEffects.asSharedFlow()

    init {
        refreshAppsList()
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

    fun refreshAppsList() {
        viewModelScope.launch {
            appRepository.fetchAppsFromRemote(homeViewState.value.activeCountryFilter)
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
            appRepository.fetchAppsFromDatabase(country)
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
                        onClickShowAll = {
                            sendViewEffect(
                                HomeViewEffect.NavigateToShowAllApps(
                                    country = homeViewState.value.activeCountryFilter,
                                    category = it.key
                                )
                            )
                        }
                    )
                }

            _homeViewStateFlow.update { state ->
                state.copy(categorizedApps = categorizedApps, refreshing = false)
            }
        }
    }

    private fun clickToOpenBottomSheet(app: ShowcaseAppUI) {
        sendViewEffect(HomeViewEffect.OpenBottomSheet(app))
    }

    private fun genFilterButtons(activeFilter: Country = homeViewState.value.activeCountryFilter) {
        _homeViewStateFlow.update { state ->
            state.copy(
                filterButtons = CountryFilter.getCountryFilterList(activeFilter) {
                    setCountryFilter(it)
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