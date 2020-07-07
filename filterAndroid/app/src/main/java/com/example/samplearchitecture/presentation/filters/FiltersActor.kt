package com.example.samplearchitecture.presentation.filters

import com.example.samplearchitecture.base.BaseActor
import com.example.samplearchitecture.stub.StubModelIntent
import kotlinx.coroutines.flow.*

class FiltersActor : BaseActor<FiltersContract.ViewIntent, StubModelIntent,
        FiltersContract.ViewState, FiltersContract.PartialChange>() {

    override val initialState =
        FiltersContract.ViewState()

    override fun Flow<FiltersContract.ViewIntent>.handleIntent(): Flow<FiltersContract.PartialChange> {
        val initialFlow = filterIsInstance<FiltersContract.ViewIntent.Initial>()
            .take(1)
            .map {
                FiltersContract.PartialChange.FiltersLoaded(
                    it.filters
                )
            }

        val filterChanged = filterIsInstance<FiltersContract.ViewIntent.FilterChanged>()
            .map {
                FiltersContract.PartialChange.FilterChanged(
                    it.filter
                )
            }

        return merge(
            initialFlow,
            filterChanged
        )
    }

}