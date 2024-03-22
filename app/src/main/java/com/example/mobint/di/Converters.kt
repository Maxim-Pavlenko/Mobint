package com.example.mobint.di

import androidx.room.TypeConverter
import com.example.mobint.entities.LoyaltyLevel
import com.google.gson.Gson

class Converters {
    private val gson = Gson()
    @TypeConverter
    fun loyaltyLevelToString (loyaltyLevel: LoyaltyLevel): String {
        return gson.toJson(loyaltyLevel)
    }

    @TypeConverter
    fun toLoyaltyLevel (loyaltyLevel: String): LoyaltyLevel {
        return gson.fromJson(loyaltyLevel, LoyaltyLevel::class.java)
    }
}