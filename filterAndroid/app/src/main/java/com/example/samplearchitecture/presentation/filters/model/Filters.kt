package com.example.samplearchitecture.presentation.filters.model

import android.os.Parcelable
import androidx.annotation.StringRes
import com.example.domain.matches.model.Range
import com.example.samplearchitecture.R
import kotlinx.android.parcel.Parcelize

sealed class Filters(
    open val id: String,
    @StringRes open val title: Int
) : Parcelable {

    open fun getFilterData(): Any? = null

    @Parcelize
    data class BooleanFilter(
        val type: Filter,
        val isFilterActive: Boolean? = null
    ) : Filters(type.id, type.titleRes) {
        override fun getFilterData() = isFilterActive
    }

    @Parcelize
    data class RangeFilter(
        val type: Filter,
        val rangeMax: Range,
        val rangeCurrent: Range
    ) : Filters(type.id, type.titleRes) {
        override fun getFilterData() = rangeCurrent
    }

    enum class Filter(val id: String, @StringRes val titleRes: Int,
                      val rangeMin: Int, val rangeMax: Int, val type: FilterTypes
    ) {
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
                    FilterTypes.BOOLEAN -> BooleanFilter(it)
                    FilterTypes.RANGE -> RangeFilter(
                        it, Range(it.rangeMin, it.rangeMax),
                        Range(it.rangeMin, it.rangeMax)
                    )
                }
            }
        }
    }

    enum class FilterTypes {
        BOOLEAN,
        RANGE
    }

}