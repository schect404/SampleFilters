package com.example.samplearchitecture

import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.samplearchitecture.presentation.filters.model.Filters
import com.example.samplearchitecture.presentation.main.MatchesContract
import com.example.samplearchitecture.presentation.main.MatchesFragment
import com.example.samplearchitecture.presentation.main.model.MatchesItems
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.isA
import com.nhaarman.mockitokotlin2.willReturn
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.samplearchitecture", appContext.packageName)
    }

    @get:Rule
    val activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun launchInitialDialogs() {
        launchFragmentInContainer {
            MatchesFragment.newInstance().apply {
                render(MatchesContract.ViewState(items = listOf(MatchesItems.Error), filters = Filters.Filter.generateInitialFilters()))
                R.id.rvMatches.matchView().check { view, noViewFoundException ->
                    assert((view as? RecyclerView)?.adapter?.itemCount == 1)
                }
            }
        }
    }
}

fun atPosition(position: Int, itemMatcher: Matcher<View?>): Matcher<View?>? {
    checkNotNull(itemMatcher)
    return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has item at position $position: ")
            itemMatcher.describeTo(description)
        }

        override fun matchesSafely(view: RecyclerView): Boolean {
            val viewHolder =
                view.findViewHolderForAdapterPosition(position)
                    ?: // has no item on such position
                    return false
            return itemMatcher.matches(viewHolder.itemView)
        }
    }
}

fun Int.matchView(): ViewInteraction = onView(withId(this))

fun Int.checkVisible(): ViewInteraction = matchView()
    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

fun Int.checkInvisible(): ViewInteraction = matchView()
    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)))

fun Int.checkGone(): ViewInteraction = matchView()
    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
