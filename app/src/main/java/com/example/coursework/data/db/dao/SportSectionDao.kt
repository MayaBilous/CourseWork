package com.example.coursework.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.coursework.data.db.model.DbSectionDetails
import com.example.coursework.data.db.model.DbSectionWithDetails
import com.example.coursework.data.db.model.DbSportSection

@Dao
abstract class SportSectionDao {

    @Upsert
    abstract suspend fun updateSection(dbSection: DbSportSection): Long

    @Query("DELETE FROM DbSportSection WHERE id = :sportSectionId")
    abstract suspend fun deleteSectionById(sportSectionId: Long)

    @Query("SELECT * FROM DbSportSection WHERE sectionName = :sectionName")
    abstract suspend fun findSectionByName(sectionName: String): DbSectionWithDetails?

    @Transaction
    @Query("SELECT * FROM DbSportSection WHERE id = :id")
    abstract suspend fun getSectionWithDetailsById(id: Long?): DbSectionWithDetails?

    @Transaction
    @Query("SELECT * FROM DbSportSection")
    abstract suspend fun getSectionWithDetails(): List<DbSectionWithDetails>

    @Transaction
    @Delete
    abstract suspend fun delete (dbSectionWithDetails: DbSectionWithDetails)
}