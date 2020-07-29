package com.example.samplearchitecture.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

fun View.clicks(): Flow<View> {
    return callbackFlow {
        setOnClickListener { offer(it) }
        awaitClose { setOnClickListener(null) }
    }
}

fun Chip.clicksOnCancel(): Flow<View> {
    return callbackFlow {
        setOnCloseIconClickListener { offer(it) }
        awaitClose { setOnCloseIconClickListener(null) }
    }
}

fun EditText.textChanges(): Flow<CharSequence?> {
    return callbackFlow<CharSequence?> {
        val listener = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                offer(s)
            }
        }
        addTextChangedListener(listener)
        awaitClose { removeTextChangedListener(listener) }
    }.onStart { emit(text) }
}

fun ChipGroup.chipChanges(): Flow<Int> {
    return callbackFlow {
        setOnCheckedChangeListener { group, checkedId ->
            offer(checkedId)
        }
        awaitClose { setOnCheckedChangeListener(null) }
    }
}

fun CrystalRangeSeekbar.rangeChanges() =
    callbackFlow {
        setOnRangeSeekbarChangeListener { minValue, maxValue ->
            offer(minValue to maxValue)
        }
        awaitClose { setOnRangeSeekbarChangeListener(null) }
    }

fun CrystalRangeSeekbar.rangeApplies() =
    callbackFlow {
        setOnRangeSeekbarFinalValueListener { minValue, maxValue ->
            offer(minValue to maxValue)
        }
        awaitClose { setOnRangeSeekbarFinalValueListener(null) }
    }