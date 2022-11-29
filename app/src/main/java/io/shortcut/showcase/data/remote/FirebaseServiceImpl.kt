package io.shortcut.showcase.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import io.shortcut.showcase.di.FirebaseService
import io.shortcut.showcase.domain.model.ShowcaseAppAPI
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseServiceImpl @Inject constructor() : FirebaseService() {
    private val firebaseDatabase = FirebaseFirestore.getInstance()

    override suspend fun getApps(): List<ShowcaseAppAPI?> {
        val snapshot = firebaseDatabase.collection("apps").get().await()
        return snapshot.toObjects(ShowcaseAppAPI::class.java)
    }
}