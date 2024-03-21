package com.example.mobint.di

import com.example.mobint.data.paging.CompanyPagingSource
import com.example.mobint.data.remote.RetrofitInstance
import com.example.mobint.data.repository.CompanyRepository
import org.koin.dsl.module

val dataModule = module {

    single<CompanyRepository> {
        CompanyRepository(get())
    }

    factory<CompanyPagingSource> {
        CompanyPagingSource(get())
    }
}