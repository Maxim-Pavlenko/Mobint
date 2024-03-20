package com.example.mobint.entities

data class LoyaltyLevel(
    val number: Int,
    val name: String,
    val requiredSum: Int,
    val markToCash: Int,
    val cashToMark: Int
)