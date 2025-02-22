package com.example.coursework.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbSection(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "section_name") val sectionName: String,
    @ColumnInfo(name = "district") val district: String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "working_days") val workingDays: String,
    @ColumnInfo(name = "phone_number") val phoneNumber: Int,
)
