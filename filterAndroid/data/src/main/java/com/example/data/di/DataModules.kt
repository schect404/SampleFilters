package com.example.data.di

import com.example.data.matches.api.MatchesApi
import com.example.data.matches.repository.MatchesRepositoryImpl
import com.example.data.retrofit.RetrofitFactory
import com.example.data.retrofit.RetrofitFactoryImpl
import com.example.domain.matches.gateway.MatchesRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module(override = true) {
    single<Gson> { GsonBuilder().setLenient().create() }
    single { OkHttpClient.Builder().build() }
    single<RetrofitFactory> { RetrofitFactoryImpl(get()) }
    single<Retrofit> { get<RetrofitFactory>().createRetrofit(get()) }

    single { get<Retrofit>().create(MatchesApi::class.java) }

    single<MatchesRepository> { MatchesRepositoryImpl(get()) }
}