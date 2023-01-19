package io.shortcut.showcase.presentation.common.filter.data

import io.shortcut.showcase.data.mapper.Country

data class CountryFilter(
    val type: Country,
    val selected: Boolean,
    val onClick: () -> Unit
) {
    companion object {
        fun getCountryFilterList(
            activeFilter: Country,
            onEvent: (Country) -> Unit
        ): List<CountryFilter> {
            val countryFilter = Country.values()
            return countryFilter.mapNotNull { country ->
                if (country != Country.Unknown) {
                    CountryFilter(
                        type = country,
                        selected = activeFilter == country,
                        onClick = { onEvent(country) }
                    )
                } else {
                    null
                }
            }
        }

    }
}
