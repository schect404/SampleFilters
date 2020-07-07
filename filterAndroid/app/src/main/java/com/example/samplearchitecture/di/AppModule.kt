package com.example.samplearchitecture.di

import com.example.samplearchitecture.presentation.filters.delegate.FiltersDelegate
import com.example.samplearchitecture.presentation.filters.delegate.FiltersDelegateImpl
import org.koin.dsl.module.module
import org.koin.experimental.builder.singleBy

val appModule = module {
    singleBy<FiltersDelegate, FiltersDelegateImpl>()
}