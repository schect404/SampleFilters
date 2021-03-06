package com.example.samplearchitecture

import com.example.domain.matches.model.DomainFilters
import com.example.domain.matches.model.Range
import com.example.samplearchitecture.base.parseError
import com.example.samplearchitecture.presentation.filters.binding.toIdRes
import com.example.samplearchitecture.presentation.filters.model.AvailableFilters
import com.example.samplearchitecture.presentation.filters.model.Filters
import com.example.samplearchitecture.presentation.filters.model.toFilters
import com.google.gson.JsonObject
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import kotlin.random.Random
import kotlin.random.nextInt

class MatchesTest {

    @Test
    fun `filters conversion to list from domain`() {
        for (i in 0..50) {
            //GIVEN
            val hasAvatar = randomizeBooleanFilter()
            val hasContacts = randomizeBooleanFilter()
            val favourite = randomizeBooleanFilter()
            val compatibilityScore = randomizeRangeFilter(1..99)
            val age = randomizeRangeFilter(18..95)
            val height = randomizeRangeFilter(135..215)

            val initialFilters = arrayListOf<Filters>().apply {
                add(Filters.BooleanFilter(AvailableFilters.HAS_AVATAR, hasAvatar))
                add(Filters.BooleanFilter(AvailableFilters.HAS_CONTACTS, hasContacts))
                add(Filters.BooleanFilter(AvailableFilters.FAVOURITE, favourite))
                add(
                    Filters.RangeFilter(
                        AvailableFilters.COMPATIBILITY_SCORE,
                        Range(1, 99),
                        compatibilityScore
                    )
                )
                add(Filters.RangeFilter(AvailableFilters.AGE, Range(18, 95), age))
                add(Filters.RangeFilter(AvailableFilters.HEIGHT, Range(135, 215), height))
            }

            val filtersExpected = DomainFilters(
                hasAvatar = hasAvatar,
                hasContact = hasContacts,
                inFavourites = favourite,
                age = age,
                height = height,
                compatibilityScore = compatibilityScore
            )

            //WHEN
            val actualFilters = initialFilters.toFilters()

            //THEN
            assert(actualFilters.isSimilar(filtersExpected))
        }
    }

    @Test
    fun `boolean filter value conversion to IdREs`() {
        for(i in 0..10) {
            //GIVEN
            val filterValue =
                Filters.BooleanFilter(AvailableFilters.HAS_AVATAR, randomizeBooleanFilter())

            //WHEN
            val idRes = filterValue.toIdRes()

            //THEN
            assert(
                idRes == when (filterValue.isFilterActive) {
                    null -> R.id.chipNotDefined
                    true -> R.id.chipYes
                    false -> R.id.chipNo
                }
            )
        }
    }

    @Test
    fun `check parse error`() {
        //GIVEN
        val apiErrorText = "Api error"
        val body = JsonObject().apply {
            addProperty("text", apiErrorText)
        }.toString()
        val exception = HttpException(createResponseWithCodeAndJson(400, body))
        //WHEN
        val exceptionError = exception.parseError()
        //THEN
        assert(exceptionError == apiErrorText)
    }

    private fun createResponseWithCodeAndJson(
        responseCode: Int,
        json: String
    ): Response<Any> =
        Response.error(responseCode, ResponseBody.create(MediaType.parse("application/json"), json.toByteArray()))

}

private fun randomizeBooleanFilter() =
    when(Random.nextInt(0..2)) {
        0 -> false
        1 -> true
        else -> null
    }

private fun randomizeRangeFilter(range: IntRange): Range {
    val min = range.random()
    val max = Random.nextInt(min..range.last)
    return Range(min, max)
}

private fun DomainFilters.isSimilar(other: DomainFilters): Boolean =
    hasAvatar == other.hasAvatar
            && hasContact == other.hasContact
            && inFavourites == other.inFavourites
            && compatibilityScore.isSimilar(other.compatibilityScore)
            && age.isSimilar(other.age)
            && height.isSimilar(other.height)

fun Range?.isSimilar(other: Range?): Boolean {
    val otherRange = other ?: return this == null
    val thisRange = this ?: return false
    return thisRange.min == otherRange.min && thisRange.max == otherRange.max
}