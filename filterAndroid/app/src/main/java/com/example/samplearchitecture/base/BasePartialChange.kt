package com.example.samplearchitecture.base

interface BasePartialChange<T> {
    fun reduceToState(initialState: T): T
}