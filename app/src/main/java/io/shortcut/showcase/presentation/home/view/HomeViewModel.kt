package io.shortcut.showcase.presentation.home.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.shortcut.showcase.data.mapper.GeneralCategory
import io.shortcut.showcase.data.repository.HomeScreenRepositoryImpl
import io.shortcut.showcase.presentation.home.data.CategorySection
import io.shortcut.showcase.util.mock.genMockShowcaseAppUIList
import io.shortcut.showcase.util.resource.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeScreenRepositoryImpl
) : ViewModel() {

    var homeViewState by mutableStateOf( HomeViewState() )

    private val _viewEffects = MutableSharedFlow<HomeViewEffects>()
    val viewEffects = _viewEffects.asSharedFlow()

    init {
        fetchDataFromDatabase()
        genSections()
    }

    private fun sendViewEffect(effect: HomeViewEffects) {
        viewModelScope.launch {
            _viewEffects.emit(effect)
        }
    }

    private fun genSections() {
        val testSections = buildList<CategorySection> {
            GeneralCategory.values().forEach { generalCategory ->
                add(
                    CategorySection(
                        generalCategory = generalCategory,
                        apps = genMockShowcaseAppUIList(),
                        onClickShowAll = {
                            sendViewEffect(effect = HomeViewEffects.OpenBottomSheet)
                        }
                    )
                )
            }
        }
        homeViewState = homeViewState.copy(
            categorySections = testSections
        )
    }

    private fun fetchDataFromDatabase() {
        viewModelScope.launch {
            repository.fetchAppsFromDatabase().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { apps ->
                            homeViewState = homeViewState.copy(
                                apps = apps
                            )
                        }
                    }
                    is Resource.Loading -> {
                        homeViewState = homeViewState.copy(loading = true)
                    }
                    is Resource.Error -> { /* TODO */ }
                }
            }
        }
    }
}