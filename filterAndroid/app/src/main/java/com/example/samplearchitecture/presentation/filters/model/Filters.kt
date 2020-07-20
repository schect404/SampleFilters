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

    open fun copyEntity() = this

    @Parcelize
    data class BooleanFilter(
        val type: AvailableFilters,
        private var isFilterActivePrivate: Boolean? = null
    ) : Filters(type.id, type.titleRes) {

        val isFilterActive
            get() = isFilterActivePrivate

        fun setIsFilterActive(isFilterActive: Boolean?) {
            isFilterActivePrivate = isFilterActive
        }

        override fun getFilterData() = isFilterActive

        override fun copyEntity() = copy()
    }

    @Parcelize
    data class RangeFilter(
        val type: AvailableFilters,
        val rangeMax: Range,
        private var rangeCurrentPrivate: Range
    ) : Filters(type.id, type.titleRes) {

        val rangeCurrent
            get() = rangeCurrentPrivate

        fun setRangeCurrent(rangeCurrent: Range) {
            rangeCurrentPrivate = rangeCurrent
        }

        override fun getFilterData() = rangeCurrent

        override fun copyEntity() = copy()
    }

}