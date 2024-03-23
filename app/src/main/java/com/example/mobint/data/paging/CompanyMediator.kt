package com.example.mobint.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.mobint.data.local.CompanyDataBase
import com.example.mobint.data.remote.CompanyService
import com.example.mobint.data.entities.BodyRequest
import com.example.mobint.data.entities.CompanyItem
import com.example.mobint.data.entities.RemoteKeys
import com.example.mobint.util.Constants.DEFAULT_PAGE_INDEX
import com.example.mobint.util.Constants.MAX_PAGE_SIZE
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CompanyMediator(
    private val companyService: CompanyService,
    private val companyDataBase: CompanyDataBase
) : RemoteMediator<Int, CompanyItem>() {
    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, CompanyItem>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getClosestRemoteKey(state)
                    remoteKeys?.nextKey?.minus(1) ?: DEFAULT_PAGE_INDEX
                }

                LoadType.APPEND -> {
                    val remoteKeys = getLastRemoteKey(state)
                    remoteKeys?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getFirstRemoteKey(state)
                    remoteKeys?.prevKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
            }
            val requestBody = createRequestBody(page)
            val response = companyService.getAllCompanies(body = requestBody)
            val endOfPaginationReached = response.companies.isEmpty()

            companyDataBase.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    companyDataBase.companyDao().deleteAllCompany()
                    companyDataBase.remoteKeysDao().deleteAllRemoteKeys()
                }
                val prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = response.companies.map { companyItem ->
                    RemoteKeys(
                        repoId = companyItem.company.companyId, prevKey = prevKey, nextKey = nextKey
                    )
                }
                companyDataBase.remoteKeysDao().insertAll(keys)
                companyDataBase.companyDao().insertAll(response.companies)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            var errorMessage = ""
            val regex = Regex("\\b\\d{3}\\b")
            val matchResult = regex.find(exception.message.toString())
            matchResult?.value?.toIntOrNull()?.let {
                when(it) {
                    401 -> errorMessage = "Ошибка авторизации"
                    400 -> {
                        val errorBody = exception.response()?.errorBody()?.string()
                        val errorString = errorBody?.let { JSONObject(it).getString("message") } ?: ""
                        errorMessage = errorString
                    }
                    500 -> errorMessage = "Все упало"
                }
            }
            return MediatorResult.Error(Throwable(errorMessage))
        }
    }
    private fun createRequestBody(page: Int) = Gson().toJson(
        BodyRequest(
            offset = page, limit = MAX_PAGE_SIZE
        )
    ).toRequestBody("application/json".toMediaType())

    private suspend fun getFirstRemoteKey(
        state: PagingState<Int, CompanyItem>
    ): RemoteKeys? {
        return state.pages.firstOrNull() { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { companyItem ->
                companyDataBase.remoteKeysDao().remoteKeysCompanyId(companyItem.company.companyId)
            }
    }

    private suspend fun getLastRemoteKey(
        state: PagingState<Int, CompanyItem>
    ): RemoteKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { companyItem ->
                companyDataBase.remoteKeysDao().remoteKeysCompanyId(companyItem.company.companyId)
            }
    }

    private suspend fun getClosestRemoteKey(
        state: PagingState<Int, CompanyItem>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.company?.companyId?.let { repoId ->
                companyDataBase.remoteKeysDao().remoteKeysCompanyId(repoId)
            }
        }
    }
}