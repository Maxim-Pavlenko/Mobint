package com.example.mobint.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mobint.data.local.CompanyDataBase
import com.example.mobint.data.paging.CompanyMediator
import com.example.mobint.data.entities.CompanyItem
import com.example.mobint.util.Constants.MAX_PAGE_SIZE
import kotlinx.coroutines.flow.Flow


class CompanyRepository(
    private val companyDataBase: CompanyDataBase,
    private val companyMediator: CompanyMediator
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getAllCompany(pagingConfig: PagingConfig = getPageConfig()): Flow<PagingData<CompanyItem>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {companyDataBase.companyDao().getAllCompany()},
            remoteMediator = companyMediator
        ).flow
    }
    private fun getPageConfig(): PagingConfig {
        return PagingConfig(
            pageSize = MAX_PAGE_SIZE,
            enablePlaceholders = false
        )
    }
}