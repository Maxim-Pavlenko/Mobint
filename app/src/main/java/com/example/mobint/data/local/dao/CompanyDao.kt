package com.example.mobint.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mobint.entities.CompanyItem

@Dao
interface CompanyDao {
    @Query("SELECT * FROM COMPANY_TABLE")
    fun getAllCompany(): PagingSource<Int, CompanyItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(companyItem: List<CompanyItem>)

    @Query("DELETE FROM COMPANY_TABLE")
    suspend fun deleteAllCompany()
}