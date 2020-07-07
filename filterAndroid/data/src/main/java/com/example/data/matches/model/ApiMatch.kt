package com.example.data.matches.model

import com.google.gson.annotations.SerializedName

data class ApiMatch (
    @SerializedName("age")
    val age: Int?,
    @SerializedName("display_name")
    val displayName: String?,
    @SerializedName("religion")
    val religion: String?,
    @SerializedName("main_photo")
    val mainPhoto: String?,
    @SerializedName("job_title")
    val jobTitle: String?,
    @SerializedName("contacts_exchanged")
    val contacts: Int?,
    @SerializedName("height_in_cm")
    val height: Int?,
    @SerializedName("compatibility_score")
    val compatibilityScore: Double?,
    @SerializedName("favourite")
    val favourite: Boolean?
)