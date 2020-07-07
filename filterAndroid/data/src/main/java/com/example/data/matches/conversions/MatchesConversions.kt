package com.example.data.matches.conversions

import com.example.data.matches.model.ApiFilters
import com.example.data.matches.model.ApiMatch
import com.example.data.matches.model.ApiRange
import com.example.domain.matches.model.DomainFilters
import com.example.domain.matches.model.Match
import com.example.domain.matches.model.Range

fun Range.toApi() =
    ApiRange(
        min = min,
        max = max
    )

fun DomainFilters.toApi() =
    ApiFilters(
        hasAccount = hasAccount,
        hasContact = hasContact,
        favourite = favourite,
        compatibilityScore = compatibilityScore?.toApi(),
        age = age?.toApi(),
        height = height?.toApi()
    )

fun ApiMatch.toDomain() =
    Match(
        age = age ?: 0,
        displayName = displayName ?: "",
        religion = religion ?: "",
        mainPhoto = mainPhoto,
        jobTitle = jobTitle ?: "",
        contacts = contacts ?: 0,
        height = height ?: 0,
        compatibilityScore = compatibilityScore ?: 0.0,
        favourites = favourite ?: false
    )