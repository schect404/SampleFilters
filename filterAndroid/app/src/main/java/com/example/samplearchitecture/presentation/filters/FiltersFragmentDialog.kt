package com.example.samplearchitecture.presentation.filters

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import com.example.domain.matches.model.Range
import com.example.samplearchitecture.BR
import com.example.samplearchitecture.R
import com.example.samplearchitecture.base.BaseDialogFragment
import com.example.samplearchitecture.common.AsyncObservableList
import com.example.samplearchitecture.databinding.ItemBooleanFilterBinding
import com.example.samplearchitecture.databinding.ItemRangeFilterBinding
import com.example.samplearchitecture.presentation.filters.delegate.FiltersDelegate
import com.example.samplearchitecture.presentation.filters.model.Filters
import com.example.samplearchitecture.stub.StubModelIntent
import com.example.samplearchitecture.utils.*
import com.github.nitrico.lastadapter.LastAdapter
import kotlinx.android.synthetic.main.item_boolean_filter.view.*
import kotlinx.android.synthetic.main.item_range_filter.view.*
import kotlinx.android.synthetic.main.view_filters_dialog.*
import kotlinx.coroutines.flow.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class FiltersFragmentDialog : BaseDialogFragment<FiltersContract.ViewIntent, StubModelIntent,
        FiltersContract.ViewState, FiltersContract.PartialChange>() {

    private val filters: List<Filters>
        get() = arguments?.getParcelableArrayList(FILTERS) ?: emptyList()

    override val layoutRes = R.layout.view_filters_dialog

    override val actor: FiltersActor by viewModel()

    private val filtersDelegate: FiltersDelegate = get()

    private val items = AsyncObservableList<Filters>()

    private val flow = MutableStateFlow<FiltersContract.ViewIntent?>(null)

    private val adapter = LastAdapter(items, BR.item)
        .map<Filters.BooleanFilter, ItemBooleanFilterBinding>(R.layout.item_boolean_filter) {
            onCreate { holder ->
                lifecycleScope.launchWhenStarted {
                    holder.itemView.chipGroup
                        .chipChanges()
                        .onEach {
                            holder.binding.item?.onSelectFilterValue(it)
                        }
                        .collect()
                }
            }
        }
        .map<Filters.RangeFilter, ItemRangeFilterBinding>(R.layout.item_range_filter) {
            onCreate { holder ->
                lifecycleScope.launchWhenStarted {
                    holder.itemView.rangeSeekbar.rangeApplies()
                        .onEach {
                            holder.binding.item?.onSelectRange(it)
                        }
                        .collect()
                }

                lifecycleScope.launchWhenStarted {
                    holder.itemView.rangeSeekbar.rangeChanges()
                        .onEach {
                            holder.itemView.tvMin.text = it.first.toString()
                            holder.itemView.tvMax.text = it.second.toString()
                        }
                        .collect()
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvItems.attachAdapter(adapter)

        lifecycleScope.launchWhenStarted {
            bCancel.clicks()
                .onEach { dismiss() }
                .collect()
        }

        lifecycleScope.launchWhenStarted {
            bApply.clicks()
                .onEach {
                    dismiss()
                    filtersDelegate.filtersChanged(items)
                }
                .collect()
        }
        flow.value =
            FiltersContract.ViewIntent.Initial(
                savedInstanceState?.getParcelableArrayList(STATE) ?: filters.map { it.copyEntity() })
    }

    private fun Int.getFiltersActive() = when(this) {
        R.id.chipNo -> false
        R.id.chipYes -> true
        else -> null
    }

    private fun Filters.BooleanFilter.onSelectFilterValue(selection: Int) {
        setIsFilterActive(selection.getFiltersActive())
        flow.value = FiltersContract.ViewIntent.FilterChanged(this)
    }

    private fun Filters.RangeFilter.onSelectRange(range: Pair<Number, Number>) {
        setRangeCurrent(Range(range.first.toInt(), range.second.toInt()))
        flow.value = FiltersContract.ViewIntent.FilterChanged(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(STATE, ArrayList(items.list()))
    }

    override fun intents() = merge(
        flow.filterNotNull()
    )

    override fun render(state: FiltersContract.ViewState) {
        items.update(state.filters)
    }

    companion object {

        private const val FILTERS = "filters"
        private const val STATE = "state"

        fun show(activity: FragmentActivity, filter: List<Filters>?) {
            FiltersFragmentDialog()
                .apply {
                    arguments = Bundle().apply {
                        putParcelableArrayList(FILTERS, ArrayList(filter ?: listOf()))
                    }
                }
                .show(activity.supportFragmentManager, FiltersFragmentDialog::class.java.name)
        }
    }

}