package com.example.mobint.data.remote

import com.example.mobint.data.entities.CompanyResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface CompanyService {
    @POST("getAllCompanies")
    suspend fun getAllCompanies(
        @Header("Content-Type") content: String = "application/json",
        @Header("TOKEN") token: String = "123",
        @Body body: RequestBody
    ): CompanyResponse
}