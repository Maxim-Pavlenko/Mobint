package com.example.mobint.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.mobint.di.Converters
import com.example.mobint.util.Constants.COMPANY_TABLE


@Entity(tableName = COMPANY_TABLE)
@TypeConverters(Converters::class)
data class CompanyItem(
    @PrimaryKey(autoGenerate = false)
    @Embedded(prefix = "company_")
    val company: Company,
    @Embedded(prefix = "customerMarkParameters_")
    val customerMarkParameters: CustomerMarkParameters? = null,
    @Embedded(prefix = "mobileAppDashboard_")
    val mobileAppDashboard: MobileAppDashboard? = null
)