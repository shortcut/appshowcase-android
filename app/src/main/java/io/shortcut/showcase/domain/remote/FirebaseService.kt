package io.shortcut.showcase.domain.remote

import com.google.firebase.firestore.FirebaseFirestore
import io.shortcut.showcase.domain.model.ShowcaseAppAPI
import io.shortcut.showcase.domain.model.ShowcaseBannerAPI
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface FirebaseService {
    suspend fun getApps(): List<ShowcaseAppAPI?>?

    suspend fun getBanners(): List<ShowcaseBannerAPI?>?
}

class FirebaseServiceImpl @Inject constructor() : FirebaseService {
    private val firebaseDatabase = FirebaseFirestore.getInstance()

    override suspend fun getApps(): List<ShowcaseAppAPI?> {
        val snapshot = firebaseDatabase.collection("apps").get().await()
        return snapshot.toObjects(ShowcaseAppAPI::class.java)
    }

    override suspend fun getBanners(): List<ShowcaseBannerAPI?> {
        val snapshot =
            firebaseDatabase.collection(FirebaseCollection.BANNERS.collection).get().await()
        return snapshot.toObjects(ShowcaseBannerAPI::class.java)
    }
}

enum class FirebaseCollection(
    val collection: String
) {
    APPS("apps"),
    BANNERS("banners"),
    MAJORCLIENTS("majorClients")
}

/*
class FirebaseServiceImpl @Inject constructor() : FirebaseService() {
    private val firebaseDatabase = FirebaseFirestore.getInstance()

    override suspend fun getApps(): List<ShowcaseAppAPI?> {
        val snapshot = firebaseDatabase.collection("apps").get().await()
        val objects = snapshot.documents.mapNotNull {
            // There are some problems deserializing histogram data from the DB.
            // It says that it can't deserialize maps with k: Int, v: Int, but that's not the problem.
            // Firebase will sometimes give you a string value paired with a NaN, a product from
            // so called "javascript math" (Jason Toms). Underneath is a quick hack to get around that,
            // where we discard the object if it can't deserialize.
            try {
                it.toObject<ShowcaseAppAPI>()
            } catch( e: Exception){
                null
            }
        }
        return objects
    }
}*/
