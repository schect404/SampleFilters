package com.example.samplearchitecture.di

import com.example.samplearchitecture.presentation.filters.FiltersActor
import com.example.samplearchitecture.presentation.main.MatchesActor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val actorsModule = module(override = true) {
    viewModel { FiltersActor() }
    viewModel { MatchesActor(get()) }
}