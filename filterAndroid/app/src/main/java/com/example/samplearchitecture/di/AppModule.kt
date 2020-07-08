package com.example.samplearchitecture.di

import com.example.samplearchitecture.presentation.filters.delegate.FiltersDelegate
import com.example.samplearchitecture.presentation.filters.delegate.FiltersDelegateImpl
import org.koin.dsl.module

val appModule = module(override = true) {
    single<FiltersDelegate> { FiltersDelegateImpl() }
}