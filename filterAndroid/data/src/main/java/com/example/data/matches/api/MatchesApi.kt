package com.example.data.matches.api

import com.example.data.matches.model.ApiFilters
import com.example.data.matches.model.ApiMatch
import com.example.data.network.NetworkContract
import retrofit2.http.Body
import retrofit2.http.POST

interface MatchesApi {

    @POST(NetworkContract.GET_MATCHES)
    suspend fun getMatches(@Body body: ApiFilters): List<ApiMatch>

}