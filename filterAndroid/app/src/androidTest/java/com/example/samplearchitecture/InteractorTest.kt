package com.example.samplearchitecture

import androidx.test.runner.AndroidJUnit4
import com.example.domain.matches.interactor.MatchesInteractor
import com.example.domain.matches.model.DomainFilters
import com.example.domain.matches.model.Match
import com.example.domain.matches.model.Range
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.test.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.get

@RunWith(AndroidJUnit4::class)
class InteractorTest: KoinTest {

    private val interactor: MatchesInteractor = get()

    @Test
    fun testFirstMatchesNotEmpty() {
        val filters = DomainFilters(
            hasAvatar = true,
            hasContact = true,
            inFavourites = null,
            age = Range(18, 95),
            height = Range(140, 180),
            compatibilityScore = Range(34, 99)
        )

        runBlocking {
            interactor.getMatches(filters)
                .collect {
                    val isAssert = it.isPrognosed()
                    assert(isAssert)
                }
        }
    }

    fun List<Match>.isPrognosed() : Boolean {
        if((firstOrNull { it.mainPhoto == null }) != null) return false
        if(firstOrNull { it.contacts == 0 } != null) return false
        if(firstOrNull { it.age !in 18..95 } != null) return false
        if(firstOrNull { it.height !in 140..180 } != null) return false
        if(firstOrNull { (it.compatibilityScore*100).toInt() !in 34..99 } != null) return false
        return true
    }

}