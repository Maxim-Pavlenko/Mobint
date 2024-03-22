package com.example.mobint.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mobint.data.local.CompanyDataBase
import com.example.mobint.data.paging.CompanyPagingSource
import com.example.mobint.data.remote.CompanyService
import com.example.mobint.entities.CompanyItem
import com.example.mobint.util.Constants.MAX_PAGE_SIZE
import kotlinx.coroutines.flow.Flow


class CompanyRepository(
    private val companyDataBase: CompanyDataBase,
    private val companyService: CompanyService
) {
    fun letCompanyFlow(pagingConfig: PagingConfig = getPageConfig()): Flow<PagingData<CompanyItem>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {CompanyPagingSource(companyService)}
        ).flow
    }

    private fun getPageConfig(): PagingConfig {
        return PagingConfig(
            pageSize = MAX_PAGE_SIZE,
            enablePlaceholders = false
        )
    }
}