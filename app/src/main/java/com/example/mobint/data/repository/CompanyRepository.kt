package com.example.mobint.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mobint.data.local.CompanyDataBase
import com.example.mobint.entities.BodyRequest
import com.example.mobint.entities.CompanyResponse
import com.example.mobint.util.Constants.ITEMS_PER_PAGE
import com.google.gson.Gson
import com.google.gson.JsonPrimitive
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.Flow

class CompanyRepository(
    private val companyDataBase: CompanyDataBase
) {

    /*suspend fun getAllCompanies(): CompanyResponse? {
        kotlin.runCatching {
            RetrofitInstance.companyAPI.getAllCompanies(body = requestBody)
        }.fold(
            onSuccess = {
                Log.d("DATA", "ТУТ")
                db.companyDao().addCompany(it.companies.filter { it.company.companyId != null })
                return it
            },
            onFailure = {
                Log.d("ERROR", "${it.message}")
                return null
            }
        )
    }*/

    fun getAllCompanies() {

    }
}