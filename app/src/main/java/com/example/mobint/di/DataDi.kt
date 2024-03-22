package com.example.mobint.di

import com.example.mobint.data.paging.CompanyMediator
import com.example.mobint.data.paging.CompanyPagingSource
import com.example.mobint.data.remote.CompanyService
import com.example.mobint.data.remote.RetrofitInstance
import com.example.mobint.data.repository.CompanyRepository
import com.example.mobint.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single<CompanyService> {
        provideCompanyService()
    }

    single<CompanyMediator> {
        CompanyMediator(
            companyService = get(),
            companyDataBase = get()
        )
    }
    factory<CompanyPagingSource> {
        CompanyPagingSource(get())
    }

    single<CompanyRepository> {
        CompanyRepository(
            companyDataBase = get(),
            companyService = get(),
            companyMediator = get()
        )
    }
}

fun provideCompanyService(): CompanyService {
    return Retrofit
        .Builder()
        .client(
            OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            }).build()
        )
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CompanyService::class.java)
}
