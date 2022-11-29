package io.shortcut.showcase.data.repository

import io.shortcut.showcase.data.local.ShowcaseDatabase
import io.shortcut.showcase.data.mapper.toShowcaseAppEntity
import io.shortcut.showcase.data.mapper.toShowcaseAppUI
import io.shortcut.showcase.data.remote.FirebaseServiceImpl
import io.shortcut.showcase.domain.repository.HomeScreenRepository
import io.shortcut.showcase.presentation.data.ShowcaseAppUI
import io.shortcut.showcase.util.resource.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeScreenRepositoryImpl @Inject constructor(
    private val database: ShowcaseDatabase,
    private val firebaseServiceImpl: FirebaseServiceImpl
) : HomeScreenRepository {

    // The data access object used to access data from the database.
    private val dao = database.dao

    override suspend fun fetchAppsFromRemote(): Flow<Resource<List<ShowcaseAppUI>>> {
        // Here starts the data stream.
        return flow {
            // The flow starts by emitting a loading signal.
            emit(Resource.Loading(isLoading = true))

            // Variable tries to fetch all the apps.
            val remoteApps = try {
                firebaseServiceImpl.getApps()
            } catch (error: IOException) {
                emit(Resource.Error("Error, couldn't fetch apps from remote."))
                null
            }

            // Null handling of the dataset (apps)
            remoteApps?.let { apps ->
                // We delete the previous apps and insert the new data.
                dao.deleteAllApps()

                // Here we insert the new data and map it to an entity form.
                dao.insertApps(apps.map { it?.toShowcaseAppEntity() ?: return@flow })

                // We emit a success signal, then fetch and re-map the data to a displayable form.
                emit(Resource.Success(data = dao.fetchAllApps().map { it.toShowcaseAppUI() }))

                // Once we are done, we emit a signal that loading is done.
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun fetchAppsFromDatabase(): Flow<Resource<List<ShowcaseAppUI>>> {
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
                emit(Resource.Success(data = dao.fetchAllApps().map { it.toShowcaseAppUI() }))
                emit(Resource.Loading(false))
            }

            // Just for safety, we emit another loading false signal.
            emit(Resource.Loading(false))
        }
    }

}