package io.shortcut.showcase.domain.model

import com.google.firebase.firestore.DocumentId

data class ShowcaseBannerAPI(
    @DocumentId
    val id: String = "",
    val imageUrl: String = ""
)
