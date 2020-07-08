package com.example.samplearchitecture.utils

import android.view.View

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.visibleIfOrGone(predicate: () -> Boolean) {
    if (predicate()) visible() else gone()
}

fun View.visibleIfOrInvisible(predicate: () -> Boolean) {
    if (predicate()) visible() else invisible()
}

fun View.disable() {
    alpha = 0.7f
    isClickable = false
    isEnabled = false
}

fun View.enable() {
    alpha = 1f
    isClickable = true
    isEnabled = true
}

fun View.enableIf(predicate: () -> Boolean) {
    if (predicate()) enable() else disable()
}

