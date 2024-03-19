package com.example.mobint.data.remote

import retrofit2.http.POST

interface CardApi {

    @POST
    suspend fun getAllCard
}