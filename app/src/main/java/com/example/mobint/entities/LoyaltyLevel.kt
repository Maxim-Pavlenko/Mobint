package com.example.mobint.entities

data class LoyaltyLevel(
    val number: Int? = null,
    val name: String = "",
    val requiredSum: Int? = null,
    val markToCash: Int? = null,
    val cashToMark: Int? = null
)