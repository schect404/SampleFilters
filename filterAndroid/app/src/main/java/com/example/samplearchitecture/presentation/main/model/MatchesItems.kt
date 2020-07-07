package com.example.samplearchitecture.presentation.main.model

import android.os.Parcelable
import com.example.domain.matches.model.Match
import kotlinx.android.parcel.Parcelize

sealed class MatchesItems: Parcelable {

    @Parcelize
    data class AppMatch(val match: Match): MatchesItems()

    @Parcelize
    object Skeleton: MatchesItems()

    @Parcelize
    object Error: MatchesItems()

}