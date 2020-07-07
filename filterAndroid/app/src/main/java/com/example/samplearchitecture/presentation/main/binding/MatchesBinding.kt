package com.example.samplearchitecture.presentation.main.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.samplearchitecture.R
import com.example.samplearchitecture.presentation.filters.model.Filters
import com.example.samplearchitecture.utils.image
import com.example.samplearchitecture.utils.visibleIfOrGone
import com.google.android.material.chip.Chip

object MatchesBinding {

    @JvmStatic
    @BindingAdapter("app:image")
    fun setRange(imageView: ImageView, url: String?) {
        imageView.image(url, R.drawable.ic_avatar)
    }

    @JvmStatic
    @BindingAdapter("app:score")
    fun setRange(textView: TextView, score: Double) {
        textView.text = "${(score*100).toInt()} %"
    }

    @JvmStatic
    @BindingAdapter("app:value", "app:label", requireAll = true)
    fun setRange(textView: TextView, value: String, label: String) {
        textView.text = "%s: %s".format(label, value)
    }

    @JvmStatic
    @BindingAdapter("app:visibleIf")
    fun provideVisibility(view: View, visible: Boolean) {
        view.visibleIfOrGone { visible }
    }

    @JvmStatic
    @BindingAdapter("app:filter")
    fun provideFilter(view: Chip, filter: Filters.RangeFilter) {
        view.text = "%s: %s-%s".format(view.context.getString(filter.title),
            filter.rangeCurrent.min.toString(),
            filter.rangeCurrent.max.toString())
    }

    @JvmStatic
    @BindingAdapter("app:filter")
    fun provideFilter(view: Chip, filter: Filters.BooleanFilter) {
        view.visibleIfOrGone { filter.isFilterActive != null }
        view.text = "%s: %s".format(view.context.getString(filter.title), filter.isFilterActive?.toString())
    }

}