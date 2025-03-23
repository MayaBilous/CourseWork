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

    @Upsert
    abstract suspend fun updateDetails(dbSectionDetails: List<DbSectionDetails>)

    @Query("DELETE FROM DbSportSection WHERE id = :sportSectionId")
    abstract suspend fun deleteSectionById(sportSectionId: Long)

    @Query("DELETE FROM DbSectionDetails WHERE id = :detailsId")
    abstract suspend fun deleteDetailsById(detailsId: Long)

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
    suspend fun delete (dbSectionWithDetails: DbSectionWithDetails){
        deleteSectionById(dbSectionWithDetails.sportSection.id ?:0)
        dbSectionWithDetails.sectionDetails.forEach {
            deleteDetailsById(it.id ?:0)
        }
    }
}