package com.example.samplearchitecture.presentation.filters.model

import com.example.domain.matches.model.DomainFilters
import com.example.domain.matches.model.Range

fun List<Filters>.toFilters() =
    DomainFilters (
        hasAccount = Filters.Filter.HAS_AVATAR.getFilterData(this) as? Boolean,
        hasContact = Filters.Filter.HAS_CONTACTS.getFilterData(this) as? Boolean,
        favourite = Filters.Filter.FAVOURITE.getFilterData(this) as? Boolean,
        compatibilityScore = Filters.Filter.COMPATIBILITY_SCORE.getFilterData(this) as? Range,
        age = Filters.Filter.AGE.getFilterData(this) as? Range,
        height = Filters.Filter.HEIGHT.getFilterData(this) as? Range
    )