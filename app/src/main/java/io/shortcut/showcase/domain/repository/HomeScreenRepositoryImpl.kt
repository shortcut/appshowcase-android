package io.shortcut.showcase.domain.repository

import io.shortcut.showcase.data.local.ShowcaseDAO
import io.shortcut.showcase.data.mapper.Country
import io.shortcut.showcase.data.mapper.toShowcaseAppEntity
import io.shortcut.showcase.data.mapper.toShowcaseAppUI
import io.shortcut.showcase.data.mapper.toShowcaseBannerUI
import io.shortcut.showcase.domain.remote.FirebaseService
import io.shortcut.showcase.presentation.data.ShowcaseAppUI
import io.shortcut.showcase.presentation.data.ShowcaseBannerUI
import io.shortcut.showcase.util.resource.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeScreenRepositoryImpl @Inject constructor(
    private val dao: ShowcaseDAO,
    private val firebaseService: FirebaseService
) : HomeScreenRepository {

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
                // We delete the previous apps and insert the new data.
                dao.deleteAllApps()
                // Update the data to view
                val entityList = apps.mapNotNull { it?.toShowcaseAppEntity() }
                // Once we are done, we emit a signal that loading is done.
                emit(Resource.Loading(false))
                // Here we insert the new data and map it to an entity form.
                dao.insertApps(entityList)
                emit(
                    Resource.Success(
                        data = dao.fetchAppsWithCountry(selectedCountry.name)
                            .map { it.toShowcaseAppUI() })
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
                emit(Resource.Loading(false))
            } else {
                // If it isn't empty, we fetch the data, map the objects -
                // then set loading to false.
                emit(
                    Resource.Success(
                        data = dao.fetchAppsWithCountry(activeCountryFilter.name)
                            .map { it.toShowcaseAppUI() })
                )
                emit(Resource.Loading(false))
            }

            // Just for safety, we emit another loading false signal.
            emit(Resource.Loading(false))
        }
    }

    override suspend fun fetchBanners(): Flow<Resource<List<ShowcaseBannerUI>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))

            val remoteBanners = try {
                firebaseService.getBanners()
            } catch (error: IOException) {
                emit(Resource.Error("Couldn't fetch banners from remote."))
                null
            }

            remoteBanners?.let { banners ->
                emit(Resource.Success(data = banners.map { it?.toShowcaseBannerUI() ?: return@flow }))
                emit(Resource.Loading(false))
            }

            emit(Resource.Loading(false))
        }
    }
}