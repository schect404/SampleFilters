package com.example.samplearchitecture.presentation.filters.model

import androidx.annotation.StringRes
import com.example.domain.matches.model.Range
import com.example.samplearchitecture.R

enum class AvailableFilters(val id: String, @StringRes val titleRes: Int,
                            val rangeMin: Int, val rangeMax: Int, val type: FilterTypes) {
    HAS_AVATAR("has_avatar", R.string.has_avatar, 0, 0, FilterTypes.BOOLEAN),
    HAS_CONTACTS("has_contacts", R.string.has_contacts, 0, 0, FilterTypes.BOOLEAN),
    FAVOURITE("favourite", R.string.in_favourite, 0, 0, FilterTypes.BOOLEAN),
    COMPATIBILITY_SCORE("compatibility", R.string.compatibility_score, 1, 99, FilterTypes.RANGE),
    AGE("age", R.string.age, 18, 95, FilterTypes.RANGE),
    HEIGHT("height", R.string.height, 135, 210, FilterTypes.RANGE);

    fun getFilterData(list: List<Filters>) = list.firstOrNull { it.id == id }?.getFilterData()

    companion object {
        fun generateInitialFilters() = values().map {
            when(it.type) {
                FilterTypes.BOOLEAN -> Filters.BooleanFilter(it)
                FilterTypes.RANGE -> Filters.RangeFilter(
                    it, Range(it.rangeMin, it.rangeMax),
                    Range(it.rangeMin, it.rangeMax)
                )
            }
        }
    }
}