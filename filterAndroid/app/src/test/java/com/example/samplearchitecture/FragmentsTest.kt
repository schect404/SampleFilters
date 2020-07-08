package com.example.samplearchitecture

import android.os.Build
import android.view.View
import android.widget.Button
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.samplearchitecture.presentation.filters.FiltersFragmentDialog
import com.example.samplearchitecture.presentation.main.MatchesFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class FragmentsTest {

    private val activityScenario = Robolectric.buildActivity(MainActivity::class.java).create()
    private val fragmentManager = activityScenario.get().supportFragmentManager

    private var matchesFragment: MatchesFragment? = null

    @Before
    fun startAll() {
        activityScenario.postCreate(null)
        fragmentManager.executePendingTransactions()
        matchesFragment = fragmentManager.fragments.filterIsInstance<MatchesFragment>().firstOrNull()
    }

    @Test
    fun matchesFragmentInjected() {
        assert(matchesFragment != null)
    }

    @Test
    fun dialogAppearsOnClickButton() {
        //GIVEN
        val scenario = launchFragmentInContainer<MatchesFragment>(themeResId = R.style.AppTheme)
        scenario.moveToState(Lifecycle.State.STARTED)
        scenario.onFragment {
            //WHEN
            it.performFilterClick()
            it.parentFragmentManager.executePendingTransactions()
            //THEN
            assert(it.parentFragmentManager.findFragmentByTag(FiltersFragmentDialog::class.java.name) != null)
        }
    }

    @Test
    fun dialogClosesOnCancel() {
        //GIVEN
        val scenario = launchFragmentInContainer<FiltersFragmentDialog>(themeResId = R.style.AppTheme)
        scenario.moveToState(Lifecycle.State.STARTED)
        scenario.onFragment {
            //WHEN
            it.performCancel()
            //THEN
            assert(!it.showsDialog)
        }
    }

    @Test
    fun dialogClosesOnApply() {
        //GIVEN
        val scenario = launchFragmentInContainer<FiltersFragmentDialog>(themeResId = R.style.AppTheme)
        scenario.moveToState(Lifecycle.State.STARTED)
        scenario.onFragment {
            //WHEN
            it.performApply()
            //THEN
            assert(!it.showsDialog)
        }
    }

    private fun MatchesFragment.performFilterClick() {
        view?.findViewById<Button>(R.id.bFilter).performClick()
    }

    private fun FiltersFragmentDialog.performCancel() {
        view?.findViewById<Button>(R.id.bCancel).performClick()
    }

    private fun FiltersFragmentDialog.performApply() {
        view?.findViewById<Button>(R.id.bApply).performClick()
    }

    private fun FiltersFragmentDialog.getRecyclerView() =
        view?.findViewById<RecyclerView>(R.id.rvFilters)

    private fun Button?.performClick() {
        assert(this != null)
        assert(this?.visibility == View.VISIBLE)
        this?.performClick()
    }

}