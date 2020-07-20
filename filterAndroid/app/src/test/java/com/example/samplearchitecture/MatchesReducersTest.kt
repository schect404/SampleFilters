package com.example.samplearchitecture

import android.os.Build
import com.example.domain.matches.model.Match
import com.example.samplearchitecture.presentation.filters.model.AvailableFilters
import com.example.samplearchitecture.presentation.filters.model.Filters
import com.example.samplearchitecture.presentation.main.MatchesContract
import com.example.samplearchitecture.presentation.main.model.MatchesItems
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class MatchesReducersTest {

    @Test
    fun checkSkeletonMatchesReducer() {
        //GIVEN
        val currentViewState = MatchesContract.ViewState()
        val skeletons = arrayListOf<MatchesItems.Skeleton>().apply {
            for(i in 1..10) add(MatchesItems.Skeleton)
        }
        val expectedViewState = currentViewState.copy(items = skeletons)

        //WHEN
        val actualViewState = MatchesContract.PartialChange.Skeletons.reduceToState(currentViewState)

        //THEN
        assert(actualViewState == expectedViewState)
    }

    @Test
    fun checkErrorMatchesReducer() {
        //GIVEN
        val currentViewState = MatchesContract.ViewState()
        val skeletons = listOf(MatchesItems.Error)
        val expectedViewState = currentViewState.copy(items = skeletons)

        //WHEN
        val actualViewState = MatchesContract.PartialChange.Error.reduceToState(currentViewState)

        //THEN
        assert(actualViewState == expectedViewState)
    }

    @Test
    fun checkFiltersInMatchesReducer() {
        //GIVEN
        val currentViewState = MatchesContract.ViewState()
        val filterToChange = Filters.BooleanFilter(AvailableFilters.HAS_AVATAR, false)

        val filtersMutable = currentViewState.filters.toMutableList()

        filtersMutable.forEachIndexed { index, filter ->
            if(filter.id == filterToChange.id) filtersMutable[index] = filterToChange
        }

        val expectedViewState = currentViewState.copy(filters = filtersMutable)

        //WHEN
        val actualViewState = MatchesContract.PartialChange.FiltersChanged(filtersMutable).reduceToState(currentViewState)

        //THEN
        assert(actualViewState == expectedViewState)
    }

    @Test
    fun checkMatchesReducer() {
        //GIVEN
        val currentViewState = MatchesContract.ViewState()
        val matches = makeMatchesMock()
        val expectedViewState = currentViewState.copy(items = matches)

        //WHEN
        val actualViewState = MatchesContract.PartialChange.ItemsLoaded(matches).reduceToState(currentViewState)

        //THEN
        assert(actualViewState == expectedViewState)
    }

}


private fun makeMatchesMock() = listOf(
    Match(
        age = 23,
        displayName = "aasdv",
        religion = "christianity",
        mainPhoto = null,
        jobTitle = "some job",
        contacts = 12,
        height = 160,
        compatibilityScore = 0.60,
        inFavourites = false
    ),
    Match(
        age = 45,
        displayName = "aasddfndfnv",
        religion = "chrifdngstianity",
        mainPhoto = null,
        jobTitle = "somefdng job",
        contacts = 12,
        height = 167,
        compatibilityScore = 0.80,
        inFavourites = true
    ),
    Match(
        age = 23,
        displayName = "aasderh4rthrtghv",
        religion = "christianitrthrthy",
        mainPhoto = null,
        jobTitle = "some jrthrthob",
        contacts = 6,
        height = 180,
        compatibilityScore = 0.90,
        inFavourites = false
    )
).map { MatchesItems.AppMatch(it) }
