package com.example.samplearchitecture.presentation.filters.delegate

import com.example.samplearchitecture.presentation.filters.model.Filters
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface FiltersDelegate {
    val filtersFlow: StateFlow<List<Filters>?>

    fun filtersChanged(list: List<Filters>)
}

class FiltersDelegateImpl : FiltersDelegate {

    override val filtersFlow = MutableStateFlow<List<Filters>?>(null)

    override fun filtersChanged(list: List<Filters>) {
        filtersFlow.value = list.toList()
    }

}