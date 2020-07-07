package com.example.samplearchitecture.di

import com.example.samplearchitecture.presentation.filters.FiltersActor
import com.example.samplearchitecture.presentation.main.MatchesActor
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val actorsModule = module {
    viewModel { FiltersActor() }
    viewModel { MatchesActor(get()) }
}