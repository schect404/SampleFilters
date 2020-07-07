package com.example.data.matches.repository

import com.example.data.matches.api.MatchesApi
import com.example.data.matches.conversions.toApi
import com.example.data.matches.conversions.toDomain
import com.example.domain.matches.gateway.MatchesRepository
import com.example.domain.matches.model.DomainFilters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MatchesRepositoryImpl(private val api: MatchesApi) : MatchesRepository {

    override fun getMatches(filters: DomainFilters) =
        flow {
            val matches = api.getMatches(filters.toApi())
            val domainMatches = matches.map { it.toDomain() }
            emit(domainMatches)
        }
        .catch { emit(listOf()) }
        .flowOn(Dispatchers.IO)

}