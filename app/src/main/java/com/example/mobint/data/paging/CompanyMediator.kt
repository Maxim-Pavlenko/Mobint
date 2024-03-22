package com.example.mobint.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.mobint.data.local.CompanyDataBase
import com.example.mobint.data.remote.CompanyService
import com.example.mobint.entities.BodyRequest
import com.example.mobint.entities.CompanyItem
import com.example.mobint.entities.RemoteKeys
import com.example.mobint.util.Constants.DEFAULT_PAGE_INDEX
import com.example.mobint.util.Constants.MAX_PAGE_SIZE
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
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

        return try {
            val requestBody = Gson().toJson(
                BodyRequest(
                    offset = page, limit = MAX_PAGE_SIZE
                )
            ).toRequestBody("application/json".toMediaType())

            val response = companyService.getAllCompanies(body = requestBody)
            val endOfPaginationReached = response.companies.isEmpty()

            companyDataBase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    companyDataBase.companyDao().deleteAllCompany()
                    companyDataBase.remoteKeysDao().deleteAllRemoteKeys()
                }
                val prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = response.companies.map { companyItem ->
                    RemoteKeys(
                        repoId = companyItem.company.companyId,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                companyDataBase.remoteKeysDao().insertAll(keys)
                companyDataBase.companyDao().insertAll(response.companies)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }

    }

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