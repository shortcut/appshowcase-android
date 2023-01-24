package io.shortcut.showcase.domain.repository

import io.shortcut.showcase.data.mapper.toShowcaseBannerUI
import io.shortcut.showcase.domain.remote.FirebaseService
import io.shortcut.showcase.presentation.data.ShowcaseBannerUI
import io.shortcut.showcase.util.resource.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeScreenRepositoryImpl @Inject constructor(
    private val firebaseService: FirebaseService
) : HomeScreenRepository {

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