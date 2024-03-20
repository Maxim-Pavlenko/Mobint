package com.example.mobint.data.repository

import android.util.Log
import com.example.mobint.data.remote.RetrofitInstance
import com.example.mobint.entities.BodyRequest
import com.example.mobint.entities.CompanyResponse
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

class CompanyRepository() {
    val request = BodyRequest(offset = 0, limit = 5)
    val requestBody = Gson().toJson(request).toRequestBody("application/json".toMediaType())
    suspend fun getAllCompanies(): CompanyResponse? {
        kotlin.runCatching {
            RetrofitInstance.companyAPI.getAllCompanies(body = requestBody)
        }.fold(
            onSuccess = {
                return it
            },
            onFailure = {
                Log.d("ERROR", "${it.message}")
                return null
            }
        )
    }
}