package com.example.samplearchitecture

import android.os.Build
import com.example.samplearchitecture.presentation.filters.FiltersContract
import com.example.samplearchitecture.presentation.filters.model.AvailableFilters
import com.example.samplearchitecture.presentation.filters.model.Filters
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class FiltersReducersTest {

    @Test
    fun checkFiltersReducing() {
        //GIVEN
        val initialViewState = FiltersContract.ViewState()
        val filters = AvailableFilters.generateInitialFilters()

        val expectedViewState = initialViewState.copy(filters = filters)

        //WHEN
        val actualViewState = FiltersContract.PartialChange.FiltersLoaded(filters).reduceToState(initialViewState)

        //THEN
        assert(actualViewState == expectedViewState)

    }

    @Test
    fun checkFilterChangesReducing() {
        //GIVEN
        val initialViewState = FiltersContract.ViewState()
        val filters = AvailableFilters.generateInitialFilters()

        val currentViewState = initialViewState.copy(filters = filters)

        val filterToChange = Filters.BooleanFilter(AvailableFilters.HAS_AVATAR, false)

        val filtersMutable = currentViewState.filters.toMutableList()

        filtersMutable.forEachIndexed { index, filter ->
            if(filter.id == filterToChange.id) filtersMutable[index] = filterToChange
        }

        val expectedViewState = currentViewState.copy(filters = filtersMutable)

        //WHEN
        val actualViewState = FiltersContract.PartialChange.FilterChanged(filterToChange).reduceToState(currentViewState)

        //THEN
        assert(actualViewState == expectedViewState)

    }

    @Test
    fun isInitialFiltersViewStateIsEmpty() {
        //GIVEN
        val viewState = FiltersContract.ViewState()

        //WHEN
        val isEmptyFilters = viewState.filters.isEmpty()

        //THEN
        assert(isEmptyFilters)
    }

}