package com.example.domain

import com.example.domain.matches.interactor.MatchesInteractor
import com.example.domain.matches.interactor.MatchesInteractorImpl
import org.koin.dsl.module.module

val domainModule = module {
    single<MatchesInteractor> { MatchesInteractorImpl(get()) }
}