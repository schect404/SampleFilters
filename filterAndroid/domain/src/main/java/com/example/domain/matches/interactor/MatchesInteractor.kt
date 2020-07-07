package com.example.domain.matches.interactor

import com.example.domain.matches.gateway.MatchesRepository
import com.example.domain.matches.model.DomainFilters
import com.example.domain.matches.model.Match
import kotlinx.coroutines.flow.Flow

interface MatchesInteractor {

    fun getMatches(filters: DomainFilters): Flow<List<Match>>

}

class MatchesInteractorImpl(private val repository: MatchesRepository) : MatchesInteractor {

    override fun getMatches(filters: DomainFilters) = repository.getMatches(filters)

}