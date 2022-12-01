package io.shortcut.showcase.util.formatter

import io.shortcut.showcase.data.mapper.GeneralCategory
import java.util.Locale

fun formatGeneralCategory(category: String): GeneralCategory {
    return when (category.lowercase(Locale.getDefault())) {
        "navigation" -> GeneralCategory.NAVIGATION
        "health & fitness", "medical", "sports" -> GeneralCategory.HEALTH_AND_FITNESS
        "lifestyle", "other", "social networking" -> GeneralCategory.OTHER
        "travel" -> GeneralCategory.TRAVEL
        "food & drink" -> GeneralCategory.FOOD_AND_DRINK
        "shopping" -> GeneralCategory.SHOPPING
        "music", "entertainment", "magazines & newspapers", "news" -> GeneralCategory.ENTERTAINMENT
        "education" -> GeneralCategory.EDUCATION
        "reference", "utilities", "business", "finance", "productivity" -> GeneralCategory.BUSINESS
        else -> GeneralCategory.OTHER
    }
}