package com.example.samplearchitecture.presentation.filters

import com.example.samplearchitecture.base.BasePartialChange
import com.example.samplearchitecture.base.BaseViewIntent
import com.example.samplearchitecture.base.BaseViewState
import com.example.samplearchitecture.presentation.filters.model.Filters

interface FiltersContract {

    sealed class ViewIntent : BaseViewIntent {

        data class Initial(val filters: List<Filters>): ViewIntent()
        data class FilterChanged(val filter: Filters): ViewIntent()

    }

    data class ViewState(
        val filters: List<Filters> = listOf()
    ): BaseViewState

    sealed class PartialChange : BasePartialChange<ViewState> {

        data class FiltersLoaded(val filters: List<Filters>): PartialChange() {
            override fun reduceToState(initialState: ViewState) =
                initialState.copy(filters = filters)
        }

        data class FilterChanged(val filter: Filters): PartialChange() {
            override fun reduceToState(initialState: ViewState) =
                initialState.copy(filters = initialState.filters.map {
                    if(it.id == filter.id) filter else it
                })
        }

    }

}