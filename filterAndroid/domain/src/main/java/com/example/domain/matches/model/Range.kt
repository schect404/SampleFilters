package com.example.domain.matches.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Range (
    val min: Int,
    val max: Int
): Parcelable