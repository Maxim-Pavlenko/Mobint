package com.example.mobint.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mobint.data.local.dao.CompanyDao
import com.example.mobint.data.local.dao.RemoteKeysDao
import com.example.mobint.entities.CompanyItem
import com.example.mobint.entities.RemoteKeys

@Database(entities = [CompanyItem::class, RemoteKeys::class], version = 1)
abstract class CompanyDataBase: RoomDatabase() {
    abstract fun companyDao(): CompanyDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}