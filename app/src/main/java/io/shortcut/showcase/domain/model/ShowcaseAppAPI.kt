package io.shortcut.showcase.domain.model

import com.google.firebase.firestore.DocumentId

data class ShowcaseAppAPI(
    @DocumentId
    val id: String = "",
    val title: String? = null,
    val iconUrl: String? = null,
    val publisher: String? = null,
    val country: String? = null,
    val screenshots: List<String>? = null,
    val totalInstalls: String? = null,
    val shortDescription: String? = null,

    val generalCategory: String? = null,

    val highestRating: String? = null,
    // val totalHistogram: Map<String, Int> = emptyMap(),

    val androidPackageID: String? = null
)
