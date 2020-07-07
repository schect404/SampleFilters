package com.example.data.matches.model

import com.google.gson.annotations.SerializedName

data class ApiRange (
    @SerializedName("min")
    val min: Int,
    @SerializedName("max")
    val max: Int
)