package io.shortcut.showcase.presentation.home.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.shortcut.showcase.data.mapper.Country
import io.shortcut.showcase.data.mapper.GeneralCategory
import io.shortcut.showcase.data.repository.HomeScreenRepositoryImpl
import io.shortcut.showcase.presentation.common.filter.data.FilterButtonData
import io.shortcut.showcase.presentation.home.data.CategorySection
import io.shortcut.showcase.util.resource.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeScreenRepositoryImpl
) : ViewModel() {

    var homeViewState by mutableStateOf(HomeViewState())

    private val _viewEffects = MutableSharedFlow<HomeViewEffects>()
    val viewEffects = _viewEffects.asSharedFlow()

    init {
        fetchDataFromDatabase()
        genFilterButtons()
    }

    private fun sendViewEffect(effect: HomeViewEffects) {
        viewModelScope.launch {
            _viewEffects.emit(effect)
        }
    }

    fun fetchDataFromRemote() {
        viewModelScope.launch {
            repository.fetchAppsFromRemote().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { apps ->

                            // Attach on click listener
                            val appsWithOnClick = apps.map { app ->
                                app.copy(
                                    onClick = {
                                        homeViewState = homeViewState.copy(
                                            appInView = app
                                        )
                                        sendViewEffect(HomeViewEffects.OpenBottomSheet)
                                    }
                                )
                            }

                            val filteredApps = buildList { addAll(appsWithOnClick.filter { it.country == homeViewState.activeCountryFilter }) }

                            homeViewState = homeViewState.copy(
                                apps = filteredApps
                            )
                        }
                        genSections()
                    }

                    is Resource.Loading -> {
                        homeViewState = homeViewState.copy(refreshing = result.isLoading)
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
                                        homeViewState = homeViewState.copy(
                                            appInView = app
                                        )
                                        sendViewEffect(HomeViewEffects.OpenBottomSheet)
                                    }
                                )
                            }

                            val filteredApps = buildList { addAll(appsWithOnClick.filter { it.country == homeViewState.activeCountryFilter }) }

                            homeViewState = homeViewState.copy(
                                apps = filteredApps
                            )
                        }
                        genSections()
                    }

                    is Resource.Loading -> {
                        homeViewState = homeViewState.copy(loading = result.isLoading)
                    }

                    is Resource.Error -> {
                        /* TODO */
                    }
                }
            }
        }
    }

    private fun genFilterButtons() {
        val activeFilter = homeViewState.activeCountryFilter
        val countryFilter = Country.values()

        homeViewState = homeViewState.copy(
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

    private fun genSections() {
        homeViewState = homeViewState.copy(
            categorySections = buildList {
                GeneralCategory.values().forEach { generalCategory ->
                    val appList = homeViewState.apps.filter {
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

    private fun setCountryFilter(country: Country) {
        // This function is called when a filter button is clicked.
        viewModelScope.launch {
            homeViewState = homeViewState.copy(
                activeCountryFilter = country
            )

            genFilterButtons()
            fetchDataFromDatabase()
            genSections()
        }
    }
}