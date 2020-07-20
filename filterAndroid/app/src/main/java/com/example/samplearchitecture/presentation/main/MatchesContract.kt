package com.example.samplearchitecture.presentation.main

import android.os.Parcelable
import com.example.samplearchitecture.base.BasePartialChange
import com.example.samplearchitecture.base.BaseViewIntent
import com.example.samplearchitecture.base.BaseViewState
import com.example.samplearchitecture.presentation.filters.model.AvailableFilters
import com.example.samplearchitecture.presentation.filters.model.Filters
import com.example.samplearchitecture.presentation.main.model.MatchesItems
import kotlinx.android.parcel.Parcelize

interface MatchesContract {

    sealed class ViewIntent: BaseViewIntent {

        object Initial: ViewIntent()
        data class FiltersChanged(val filters: List<Filters>): ViewIntent()

    }

    @Parcelize
    data class ViewState(
        val items: List<MatchesItems> = listOf(),
        val filters: List<Filters> = AvailableFilters.generateInitialFilters()
    ): BaseViewState, Parcelable

    sealed class PartialChange : BasePartialChange<ViewState> {

        object Skeletons : PartialChange() {
            override fun reduceToState(initialState: ViewState): ViewState {
                val list = arrayListOf<MatchesItems.Skeleton>().apply {
                    for(i in 1..10) add(MatchesItems.Skeleton)
                }
                return initialState.copy(items = list)
            }
        }

        data class FiltersChanged(val filters: List<Filters>): PartialChange() {
            override fun reduceToState(initialState: ViewState) =
                initialState.copy(filters = filters)
        }

        class ItemsLoaded(val items: List<MatchesItems.AppMatch>): PartialChange() {
            override fun reduceToState(initialState: ViewState) =
                initialState.copy(items = items)
        }

        object Error : PartialChange() {
            override fun reduceToState(initialState: ViewState) =
                initialState.copy(items = listOf(MatchesItems.Error))
        }

    }

}