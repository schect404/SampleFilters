package com.example.domain.matches.model

data class DomainFilters (
    val hasAvatar: Boolean?,
    val hasContact: Boolean?,
    val inFavourites: Boolean?,
    val compatibilityScore: Range?,
    val age: Range?,
    val height: Range?
)