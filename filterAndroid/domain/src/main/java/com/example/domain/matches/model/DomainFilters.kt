package com.example.domain.matches.model

data class DomainFilters (
    val hasAccount: Boolean?,
    val hasContact: Boolean?,
    val inFavourites: Boolean?,
    val compatibilityScore: Range?,
    val age: Range?,
    val height: Range?
)