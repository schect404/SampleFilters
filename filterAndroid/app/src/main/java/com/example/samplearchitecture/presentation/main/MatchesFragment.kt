package com.example.samplearchitecture.presentation.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.samplearchitecture.BR
import com.example.samplearchitecture.R
import com.example.samplearchitecture.base.BaseFragment
import com.example.samplearchitecture.common.AsyncObservableList
import com.example.samplearchitecture.presentation.filters.FiltersFragmentDialog
import com.example.samplearchitecture.presentation.filters.delegate.FiltersDelegate
import com.example.samplearchitecture.presentation.filters.model.Filters
import com.example.samplearchitecture.presentation.main.model.MatchesItems
import com.example.samplearchitecture.stub.StubModelIntent
import com.example.samplearchitecture.utils.attachAdapter
import com.example.samplearchitecture.utils.clicks
import com.github.nitrico.lastadapter.LastAdapter
import kotlinx.android.synthetic.main.fragment_matches.*
import kotlinx.coroutines.flow.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class MatchesFragment: BaseFragment<MatchesContract.ViewIntent,
        StubModelIntent, MatchesContract.ViewState, MatchesContract.PartialChange>() {

    override val layoutRes = R.layout.fragment_matches

    override val actor: MatchesActor by viewModel()

    private val items = AsyncObservableList<MatchesItems>()

    private val filters = AsyncObservableList<Filters>()

    private val filtersDelegate: FiltersDelegate = get()

    private val flow = MutableStateFlow<MatchesContract.ViewIntent?>(null)

    private val adapter = LastAdapter(items, BR.item)
        .map<MatchesItems.AppMatch>(R.layout.item_match)
        .map<MatchesItems.Error>(R.layout.item_error)
        .map<MatchesItems.Skeleton>(R.layout.item_skeleton_match)

    private val filtersAdapter = LastAdapter(filters, BR.item)
        .map<Filters.BooleanFilter>(R.layout.item_boolean_filter_main)
        .map<Filters.RangeFilter>(R.layout.item_range_filter_main)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMatches.attachAdapter(adapter)
        rvFilters.attachAdapter(filtersAdapter)

        lifecycleScope.launchWhenStarted {
            bFilter.clicks()
                .onEach {
                    FiltersFragmentDialog.show(requireActivity(), actor.viewState.value?.filters)
                }
                .collect()
        }

        lifecycleScope.launchWhenStarted {
            filtersDelegate.filtersFlow
                .filterNotNull()
                .onEach {
                    flow.value = MatchesContract.ViewIntent.FiltersChanged(it)
                }
                .collect()
        }
    }

    override fun intents() = merge(
        flowOf(MatchesContract.ViewIntent.Initial),
        flow.filterNotNull()
    )

    override fun render(state: MatchesContract.ViewState) {
        items.update(state.items)
        filters.update(state.filters)
    }

    companion object {
        fun newInstance() = MatchesFragment()
    }

}