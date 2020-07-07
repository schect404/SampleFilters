package com.example.data.matches.model

import com.google.gson.annotations.SerializedName

data class ApiFilters (
    @SerializedName("has_avatar")
    val hasAccount: Boolean?,
    @SerializedName("has_contacts")
    val hasContact: Boolean?,
    @SerializedName("favourite")
    val favourite: Boolean?,
    @SerializedName("compatibility_score")
    val compatibilityScore: ApiRange?,
    @SerializedName("age")
    val age: ApiRange?,
    @SerializedName("height")
    val height: ApiRange?
)