package io.shortcut.showcase.presentation.home.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.shortcut.showcase.data.mapper.Country
import io.shortcut.showcase.data.mapper.GeneralCategory
import io.shortcut.showcase.domain.repository.HomeScreenRepository
import io.shortcut.showcase.presentation.common.filter.data.FilterButtonData
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
        fetchDataFromRemote()
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

    fun fetchDataFromRemote() {
        viewModelScope.launch {
            repository.fetchAppsFromRemote().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { data ->
                            // Attach on click listener
                            val appsWithOnClick = data.map { app ->
                                app.copy(
                                    onClick = {
                                        viewModelScope.launch {
                                            _homeViewStateFlow.update {
                                                it.copy(appInView = app, refreshing = false)
                                            }
                                        }
                                        sendViewEffect(HomeViewEffect.OpenBottomSheet)
                                    }
                                )
                            }

                            _homeViewStateFlow.update { state ->
                                val filteredApps =
                                    buildList { addAll(appsWithOnClick.filter { it.country == state.activeCountryFilter }) }
                                state.copy(apps = filteredApps)
                            }
                        }
                        genSections()
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

    private fun fetchDataFromDatabase() {
        viewModelScope.launch {
            repository.fetchAppsFromDatabase().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { apps ->

                            // Attach on click listener
                            val appsWithOnClick = apps.map { app ->
                                app.copy(
                                    onClick = {
                                        _homeViewStateFlow.update {
                                            it.copy(appInView = app, refreshing = false)
                                        }
                                        sendViewEffect(HomeViewEffect.OpenBottomSheet)
                                    }
                                )
                            }

                            val filteredApps =
                                buildList { addAll(appsWithOnClick.filter { it.country == _homeViewStateFlow.value.activeCountryFilter }) }
                            _homeViewStateFlow.update { state ->
                                state.copy(apps = filteredApps, refreshing = false)
                            }
                        }
                        genSections()
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

    private fun genFilterButtons() {
        val activeFilter = _homeViewStateFlow.value.activeCountryFilter
        val countryFilter = Country.values()
        _homeViewStateFlow.update {
            it.copy(
                filterButtons = buildList {
                    countryFilter.forEach { country ->
                        if (country != Country.Unknown) {
                            add(
                                FilterButtonData(
                                    type = country,
                                    selected = activeFilter == country,
                                    onClick = { setCountryFilter(country) }
                                )
                            )
                        }
                    }
                }
            )
        }
    }

    private fun genSections() {
        viewModelScope.launch {
            _homeViewStateFlow.update { state ->
                state.copy(
                    categorySections = buildList {
                        GeneralCategory.values().forEach { generalCategory ->
                            val appList = state.apps.filter {
                                it.generalCategory == generalCategory
                            }

                            if (appList.isNotEmpty()) {
                                add(
                                    CategorySection(
                                        generalCategory = generalCategory,
                                        apps = appList,
                                        onClickShowAll = {}
                                    )
                                )
                            }
                        }
                    }
                )
            }
        }
    }

    private fun setCountryFilter(country: Country) {
        viewModelScope.launch {
            _homeViewStateFlow.update { state ->
                state.copy(
                    activeCountryFilter = country
                )
            }
            genFilterButtons()
            fetchDataFromDatabase()
            genSections()
        }
    }
}