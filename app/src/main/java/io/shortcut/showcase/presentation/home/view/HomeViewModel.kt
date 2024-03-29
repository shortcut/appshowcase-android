package io.shortcut.showcase.presentation.home.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.shortcut.showcase.data.mapper.Country
import io.shortcut.showcase.domain.repository.AppsRepository
import io.shortcut.showcase.domain.repository.HomeScreenRepository
import io.shortcut.showcase.presentation.common.filter.data.CountryFilter
import io.shortcut.showcase.presentation.common.topbar.SearchWidgetState
import io.shortcut.showcase.presentation.data.ShowcaseAppUI
import io.shortcut.showcase.presentation.home.data.CategorySection
import io.shortcut.showcase.presentation.showAll.SheetContent
import io.shortcut.showcase.util.resource.Resource
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeScreenRepository,
    private val appRepository: AppsRepository
) : ViewModel() {

    private val _homeAppsGridStateFlow = MutableStateFlow<HomeState>(HomeState())
    val homeViewState = _homeAppsGridStateFlow.asStateFlow()

    private val _viewEffects = MutableSharedFlow<HomeViewEffect>()
    val viewEffects = _viewEffects.asSharedFlow()

    private val countryFilterState: StateFlow<Country> =
        homeViewState.map { it.homeGridState.activeCountryFilter }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = Country.All
            )

    init {
        initHomeScreen()
    }

    private fun initHomeScreen() {
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
                            _homeAppsGridStateFlow.update { state ->
                                state.copy(
                                    homeGridState = state.homeGridState.copy(
                                        banners = banners,
                                    ),
                                    isRefreshing = false
                                )
                            }
                        }
                    }

                    is Resource.Loading -> {
                        _homeAppsGridStateFlow.update { state ->
                            state.copy(
                                isRefreshing = result.isLoading
                            )
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
            appRepository.fetchAppsFromRemote(countryFilterState.value)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            setUpHomeViewState(result)
                        }

                        is Resource.Loading -> {
                            _homeAppsGridStateFlow.update { state ->
                                state.copy(
                                    isRefreshing = result.isLoading
                                )
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
                        is Resource.Loading -> {}

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
                                    country = countryFilterState.value,
                                    category = it.key
                                )
                            )
                        }
                    )
                }
            _homeAppsGridStateFlow.update { state ->
                state.copy(
                    homeGridState = state.homeGridState.copy(categorizedApps = categorizedApps),
                    isRefreshing = false
                )
            }
        }
    }

    private fun clickToOpenBottomSheet(app: ShowcaseAppUI) {
        _homeAppsGridStateFlow.update { state ->
            state.copy(
                bottomSheet = SheetContent.AppInfo(app),
                homeAppSearchState = null,
                toolbarState = SearchWidgetState.CLOSED
            )
        }
        sendViewEffect(HomeViewEffect.OpenBottomSheet)
    }

    private fun genFilterButtons(activeFilter: Country = countryFilterState.value) {
        _homeAppsGridStateFlow.update { state ->
            state.copy(
                homeGridState = state.homeGridState.copy(
                    filterButtons = CountryFilter.getCountryFilterList(activeFilter) { country ->
                        setCountryFilter(country)
                    }
                )
            )
        }
    }

    private fun setCountryFilter(country: Country) {
        viewModelScope.launch {
            _homeAppsGridStateFlow.update { state ->
                state.copy(
                    homeGridState = state.homeGridState.copy(
                        activeCountryFilter = country
                    )
                )
            }
            genFilterButtons(country)
            fetchDataFromDatabase(country)
        }
    }

    fun hideBottomSheet() {
        sendViewEffect(HomeViewEffect.HideBottomSheet)
    }

    @OptIn(FlowPreview::class)
    fun search(query: String) {
        viewModelScope.launch {
            if (query.isBlank()) {
                setupSearchResults(query, emptyList())
            } else {
                appRepository.searchAppsWithName(query = query)
                    .collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                setupSearchResults(query, result.data)
                            }

                            is Resource.Loading -> {}
                            is Resource.Error -> {
                                /* TODO */
                            }
                        }
                    }
            }
        }
    }

    private suspend fun setupSearchResults(query: String, data: List<ShowcaseAppUI>?) {
        data?.let { apps ->
            _homeAppsGridStateFlow.update { state ->
                state.copy(
                    homeAppSearchState = HomeAppSearchState(
                        query = query,
                        searchResults = apps.map { it.copy(onClick = { clickToOpenBottomSheet(it) }) }
                    ),
                    toolbarState = SearchWidgetState.OPENED
                )
            }
        }
    }

    fun handleSearchScreen(isVisible: Boolean) {
        viewModelScope.launch {
            if (isVisible) {
                _homeAppsGridStateFlow.update { state ->
                    state.copy(
                        homeAppSearchState = HomeAppSearchState(
                            query = "",
                            searchResults = emptyList(),
                        ),
                        toolbarState = SearchWidgetState.OPENED
                    )
                }
            } else {
                _homeAppsGridStateFlow.update { state ->
                    state.copy(homeAppSearchState = null, toolbarState = SearchWidgetState.CLOSED)
                }
            }
        }
    }
}