package com.example.mobint.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mobint.util.Constants.COMPANY_TABLE
import com.google.gson.annotations.SerializedName


@Entity(tableName = COMPANY_TABLE)
data class CompanyItem(
    @PrimaryKey(autoGenerate = false)
    @Embedded(prefix = "company_")
    val company: Company,
    @Embedded(prefix = "customerMarkParameters_")
    val customerMarkParameters: CustomerMarkParameters? = null,
    @Embedded(prefix = "mobileAppDashboard_")
    val mobileAppDashboard: MobileAppDashboard? = null
)