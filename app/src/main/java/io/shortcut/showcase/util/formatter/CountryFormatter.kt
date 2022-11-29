package io.shortcut.showcase.util.formatter

import io.shortcut.showcase.data.mapper.Country
import java.util.Locale

fun formatCountry(country: String): Country {
    return when (country.lowercase(Locale.getDefault())) {
        "no" -> Country.Norway
        "se" -> Country.Sweden
        "dk" -> Country.Denmark
        else -> Country.Unknown
    }
}