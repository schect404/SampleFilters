package com.example.samplearchitecture.presentation.filters.binding

import androidx.databinding.BindingAdapter
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar
import com.example.samplearchitecture.R
import com.example.samplearchitecture.presentation.filters.model.Filters
import com.google.android.material.chip.ChipGroup

object FiltersBinding {

    @JvmStatic
    @BindingAdapter("app:choice")
    fun setChoice(chipGroup: ChipGroup, filter: Filters.BooleanFilter) {
        chipGroup.check(filter.toIdRes())
    }

    @JvmStatic
    @BindingAdapter("app:range")
    fun setRange(seekbar: CrystalRangeSeekbar, filter: Filters.RangeFilter) {
        seekbar.setMaxValue(filter.rangeMax.max.toFloat())
        seekbar.setMinValue(filter.rangeMax.min.toFloat())
        seekbar.setMaxStartValue(filter.rangeCurrent.max.toFloat())
        seekbar.setMinStartValue(filter.rangeCurrent.min.toFloat())
        seekbar.apply()
    }

}

fun Filters.BooleanFilter.toIdRes() =
    when(isFilterActive) {
        null -> R.id.chipNotDefined
        true -> R.id.chipYes
        false -> R.id.chipNo
    }