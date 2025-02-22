package com.example.coursework.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbSportSection(
    @PrimaryKey val id: Long?,
    @ColumnInfo(name = "sectionName") val sectionName: String,
    @ColumnInfo(name = "district") val district: String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "workingDays") val workingDays: String,
    @ColumnInfo(name = "phoneNumber") val phoneNumber: Int,
)
