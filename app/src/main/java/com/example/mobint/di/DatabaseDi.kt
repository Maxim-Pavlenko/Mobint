package com.example.mobint.di

import android.content.Context
import androidx.room.Room
import com.example.mobint.data.local.CompanyDataBase
import org.koin.dsl.module

fun provideDatabase(context: Context) =
    Room.inMemoryDatabaseBuilder(context, CompanyDataBase::class.java)
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

fun provideDao(db: CompanyDataBase) = db.companyDao()


val dataBaseModule = module {
    single<CompanyDataBase> {
        provideDatabase(context = get())
    }

    single {
        provideDao(get())
    }
}