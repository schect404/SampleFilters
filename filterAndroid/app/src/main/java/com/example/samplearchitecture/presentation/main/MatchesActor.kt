package com.example.samplearchitecture.presentation.main

import com.example.domain.matches.interactor.MatchesInteractor
import com.example.domain.matches.model.Match
import com.example.samplearchitecture.base.BaseActor
import com.example.samplearchitecture.presentation.filters.model.Filters
import com.example.samplearchitecture.presentation.filters.model.toFilters
import com.example.samplearchitecture.presentation.main.model.MatchesItems
import com.example.samplearchitecture.stub.StubModelIntent
import kotlinx.coroutines.flow.*

class MatchesActor(private val matchesInteractor: MatchesInteractor) : BaseActor<MatchesContract.ViewIntent,
        StubModelIntent, MatchesContract.ViewState, MatchesContract.PartialChange>()  {

    override val initialState = MatchesContract.ViewState()

    override fun Flow<MatchesContract.ViewIntent>.handleIntent(): Flow<MatchesContract.PartialChange> {
        val initialFlow = filterIsInstance<MatchesContract.ViewIntent.Initial>()
            .take(1)
            .flatMapConcat { getMatches(initialState.filters) }

        val filtersChangedFlow = filterIsInstance<MatchesContract.ViewIntent.FiltersChanged>()
            .flatMapConcat { getMatches(it.filters) }

        return merge(
            initialFlow,
            filtersChangedFlow
        )
    }

    private fun getMatches(filters: List<Filters>) =
            flow {
                matchesInteractor.getMatches(filters.toFilters())
                    .runWithoutProgress(rethrowError = true)
                    .onEach {
                        if(it.isEmpty()) emit(MatchesContract.PartialChange.Error)
                        else emit(MatchesContract.PartialChange.ItemsLoaded(it.map {
                            MatchesItems.AppMatch(it)
                        }))
                    }
                    .onStart {
                        emit(MatchesContract.PartialChange.Skeletons)
                        emit(MatchesContract.PartialChange.FiltersChanged(filters))
                    }
                    .catch { emit(MatchesContract.PartialChange.Error) }
                    .collect()
            }

}