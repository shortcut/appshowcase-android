package io.shortcut.showcase.util.formatter

import io.shortcut.showcase.data.mapper.GeneralCategory
import java.util.Locale

fun formatGeneralCategory(category: String): GeneralCategory {
    return when (category.lowercase(Locale.getDefault())) {
        "navigation" -> GeneralCategory.NAVIGATION
        "health_and_fitness" -> GeneralCategory.HEALTH_AND_FITNESS
        "lifestyle" -> GeneralCategory.OTHER
        "travel" -> GeneralCategory.TRAVEL
        "food_and_drink" -> GeneralCategory.FOOD_AND_DRINK
        "shopping" -> GeneralCategory.SHOPPING
        "entertainment" -> GeneralCategory.ENTERTAINMENT
        "education" -> GeneralCategory.EDUCATION
        "business" -> GeneralCategory.BUSINESS
        else -> GeneralCategory.OTHER
    }
}