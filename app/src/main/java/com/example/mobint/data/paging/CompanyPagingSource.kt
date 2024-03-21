package com.example.mobint.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mobint.data.remote.CompanyApi
import com.example.mobint.entities.BodyRequest
import com.example.mobint.entities.CompanyItem
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class CompanyPagingSource(
    private val companyApi: CompanyApi,
): PagingSource<Int, CompanyItem>() {
    override fun getRefreshKey(state: PagingState<Int, CompanyItem>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CompanyItem> {
        val page: Int = params.key ?: 1
        val pageSize: Int = params.loadSize
        val requestBody = createBody(page, pageSize)
        companyApi.getAllCompanies()
    }

    private fun createBody(page: Int, size: Int): RequestBody {
        val request = BodyRequest(offset = page, limit = size)
        return Gson().toJson(request).toRequestBody("application/json".toMediaType())
    }

}