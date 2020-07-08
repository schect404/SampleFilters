package com.example.domain

import com.example.domain.matches.interactor.MatchesInteractor
import com.example.domain.matches.interactor.MatchesInteractorImpl
import org.koin.dsl.module

val domainModule = module(override = true) {
    single<MatchesInteractor> { MatchesInteractorImpl(get()) }
}