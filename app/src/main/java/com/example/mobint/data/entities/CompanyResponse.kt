package com.example.mobint.data.entities

data class CompanyResponse(
    val companies: List<CompanyItem>,
    val limit: Int,
    val offset: Int,
    val type: String? = null,
    val message: String? = null
)