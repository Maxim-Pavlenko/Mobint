package com.example.mobint.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mobint.util.Constants.REMOTE_KEYS

@Entity(tableName = REMOTE_KEYS)
data class RemoteKeys (
    @PrimaryKey
    val repoId: String,
    val prevKey: Int?,
    val nextKey: Int?
)