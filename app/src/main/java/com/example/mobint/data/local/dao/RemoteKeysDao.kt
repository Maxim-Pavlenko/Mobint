package com.example.mobint.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mobint.data.entities.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<RemoteKeys>)

    @Query("SELECT * FROM REMOTE_KEYS WHERE repoId = :id")
    suspend fun remoteKeysCompanyId(id: String): RemoteKeys?

    @Query("DELETE FROM REMOTE_KEYS")
    suspend fun deleteAllRemoteKeys()
}