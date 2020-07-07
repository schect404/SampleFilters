package com.example.domain.matches.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Match (
    val age: Int,
    val displayName: String,
    val religion: String,
    val mainPhoto: String?,
    val jobTitle: String,
    val contacts: Int,
    val height: Int,
    val compatibilityScore: Double,
    val inFavourites: Boolean
): Parcelable