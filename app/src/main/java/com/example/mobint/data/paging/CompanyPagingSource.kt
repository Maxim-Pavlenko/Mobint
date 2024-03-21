package com.example.mobint.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mobint.data.remote.CompanyService
import com.example.mobint.data.remote.RetrofitInstance
import com.example.mobint.entities.BodyRequest
import com.example.mobint.entities.CompanyItem
import com.example.mobint.util.Constants.MAX_PAGE_SIZE
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class CompanyPagingSource(
    private val companyService: CompanyService
): PagingSource<Int, CompanyItem>() {
    override fun getRefreshKey(state: PagingState<Int, CompanyItem>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CompanyItem> {
        val page: Int = params.key ?: 1
        val pageSize: Int = params.loadSize.coerceAtMost(MAX_PAGE_SIZE)
        val requestBody = createBody(page, pageSize)
        kotlin.runCatching {
            companyService.getAllCompanies(body = requestBody)
        }.fold(
            onSuccess = {
                val nextKey = if (it.companies.size < pageSize) null else page + 1
                val prevKey = if (page == 1) null else page - 1
                return LoadResult.Page(it.companies, prevKey, nextKey)
            },
            onFailure = {
                return LoadResult.Error(it)
            }
        )
    }

    private fun createBody(page: Int, size: Int): RequestBody {
        val request = BodyRequest(offset = page, limit = size)
        return Gson().toJson(request).toRequestBody("application/json".toMediaType())
    }

}