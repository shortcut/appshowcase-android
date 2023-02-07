package io.shortcut.showcase.data.mapper

import androidx.room.TypeConverter

enum class GeneralCategory(
    val value: String
) {
    NAVIGATION("Navigation"),
    HEALTH_AND_FITNESS("Health & Fitness"),
    TRAVEL("Travel"),
    FOOD_AND_DRINK("Food & Drink"),
    SHOPPING("Shopping"),
    ENTERTAINMENT("Entertainment"),
    EDUCATION("Education"),
    BUSINESS("Business"),
    OTHER("Other");
}

class GeneralCategoryTypeConverter {
    @TypeConverter
    fun toGeneralCategoryType(value: String): GeneralCategory = enumValueOf(value)

    @TypeConverter
    fun fromGeneralCategoryType(value: GeneralCategory): String = value.name
}

fun getAllCategories() = GeneralCategory.values().map {
    it.value
}

fun getCategoryEnumFromStringValue(value: String) =
    GeneralCategory.values().find { it.value == value } ?: GeneralCategory.OTHER