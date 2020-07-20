package com.example.samplearchitecture.presentation.filters.model

import com.example.domain.matches.model.DomainFilters
import com.example.domain.matches.model.Range

fun List<Filters>.toFilters() =
    DomainFilters (
        hasAvatar = AvailableFilters.HAS_AVATAR.getFilterData(this) as? Boolean,
        hasContact = AvailableFilters.HAS_CONTACTS.getFilterData(this) as? Boolean,
        inFavourites = AvailableFilters.FAVOURITE.getFilterData(this) as? Boolean,
        compatibilityScore = AvailableFilters.COMPATIBILITY_SCORE.getFilterData(this) as? Range,
        age = AvailableFilters.AGE.getFilterData(this) as? Range,
        height = AvailableFilters.HEIGHT.getFilterData(this) as? Range
    )