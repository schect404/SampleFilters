package com.example.domain.matches.gateway

import com.example.domain.matches.model.DomainFilters
import com.example.domain.matches.model.Match
import kotlinx.coroutines.flow.Flow

interface MatchesRepository {
    fun getMatches(filters: DomainFilters): Flow<List<Match>>
}