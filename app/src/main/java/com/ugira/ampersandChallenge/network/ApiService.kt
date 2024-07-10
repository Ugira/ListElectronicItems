package com.ugira.ampersandChallenge.network

import com.ugira.ampersandChallenge.structure.ElectronicItem
import retrofit2.http.GET

interface ApiService {
    @GET("objects")
    suspend fun getObjects(): List<ElectronicItem>
}