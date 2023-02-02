package io.shortcut.showcase.domain.repository

import io.shortcut.showcase.data.local.ShowcaseDAO
import io.shortcut.showcase.data.mapper.Country
import io.shortcut.showcase.data.mapper.GeneralCategory
import io.shortcut.showcase.data.mapper.SortOrder
import io.shortcut.showcase.data.mapper.toShowcaseAppEntity
import io.shortcut.showcase.data.mapper.toShowcaseAppUI
import io.shortcut.showcase.domain.remote.FirebaseService
import io.shortcut.showcase.presentation.data.ShowcaseAppUI
import io.shortcut.showcase.util.extensions.sortAppsList
import io.shortcut.showcase.util.resource.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

interface AppsRepository {
    suspend fun fetchAppsFromRemote(selectedCountry: Country): Flow<Resource<List<ShowcaseAppUI>>>
    suspend fun fetchAppsFromDatabase(activeCountryFilter: Country): Flow<Resource<List<ShowcaseAppUI>>>
    suspend fun fetchAppsFromDatabase(
        activeCountryFilter: Country,
        selectedCategory: GeneralCategory,
        sortBy: SortOrder
    ): Flow<Resource<List<ShowcaseAppUI>>>
}

class AppsRepositoryImpl @Inject constructor(
    private val dao: ShowcaseDAO,
    private val firebaseService: FirebaseService
) : AppsRepository {

    override suspend fun fetchAppsFromRemote(selectedCountry: Country): Flow<Resource<List<ShowcaseAppUI>>> {
        // Here starts the data stream.
        return flow {
            // The flow starts by emitting a loading signal.
            emit(Resource.Loading(isLoading = true))
            // Variable tries to fetch all the apps.
            val remoteApps = try {
                firebaseService.getApps()
            } catch (error: IOException) {
                emit(Resource.Error("Error, couldn't fetch apps from remote."))
                null
            }
            // Null handling of the dataset (apps)
            remoteApps?.let { apps ->
                // Update the data to view
                val entityList = apps.mapNotNull { it?.toShowcaseAppEntity() }
                // Once we are done, we emit a signal that loading is done.
                emit(Resource.Loading(false))
                // Here we insert the new data and map it to an entity form.
                dao.insertApps(entityList)
                emit(
                    Resource.Success(
                        data = dao.fetchAppsWithCountry(
                            countries = makeListOfCountriesForQuery(
                                selectedCountry
                            )
                        ).map { it.toShowcaseAppUI() }
                    )
                )
            }
        }
    }

    override suspend fun fetchAppsFromDatabase(activeCountryFilter: Country): Flow<Resource<List<ShowcaseAppUI>>> {
        // Here starts the data stream.
        return flow {
            // The flow starts by emitting a loading signal.
            emit(Resource.Loading(isLoading = true))
            // Here we create a variable for fetching all the apps (unsorted).
            val localApps = dao.fetchAllApps()

            // Variable that checks if the database is empty.
            val isDbEmpty = localApps.isEmpty()

            // If the database is empty, we emit an error message and -
            // loading is set to false.
            if (isDbEmpty) {
                emit(Resource.Error("Error, couldn't the database."))
            } else {
                // If it isn't empty, we fetch the data, map the objects -
                // then set loading to false.
                emit(
                    Resource.Success(
                        data = dao.fetchAppsWithCountry(
                            countries = makeListOfCountriesForQuery(
                                activeCountryFilter
                            )
                        ).map { it.toShowcaseAppUI() }
                    )
                )
            }
            // Just for safety, we emit another loading false signal.
            emit(Resource.Loading(false))
        }
    }

    override suspend fun fetchAppsFromDatabase(
        activeCountryFilter: Country,
        seleCategory: GeneralCategory,
        sortBy: SortOrder
    ): Flow<Resource<List<ShowcaseAppUI>>> {
        // Here starts the data stream.
        return flow {
            // The flow starts by emitting a loading signal.
            emit(Resource.Loading(isLoading = true))

            val data = dao.fetchAppsWithCountry(
                countries = makeListOfCountriesForQuery(
                    activeCountryFilter
                ),
                category = seleCategory.name,
            ).map { it.toShowcaseAppUI() }

            emit(
                Resource.Success(data = data.sortAppsList(sortBy))
            )

            // Just for safety, we emit another loading false signal.
            emit(Resource.Loading(false))
        }
    }

    private suspend fun makeListOfCountriesForQuery(activeCountryFilter: Country): List<String> {
        val countries = if (activeCountryFilter == Country.All) {
            Country.values().map { it.name }
        } else {
            listOf(activeCountryFilter.name)
        }
        return countries
    }
}