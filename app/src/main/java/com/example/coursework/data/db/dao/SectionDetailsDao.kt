package com.example.coursework.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.coursework.data.db.model.DbSectionDetails
import com.example.coursework.data.db.model.DbSectionWithDetails

@Dao
interface SectionDetailsDao {

    @Insert
    suspend fun addSectionDetails(vararg dbSectionDetails: DbSectionDetails)

    @Update
    suspend fun updateDetails(dbSectionDetails: DbSectionDetails)

    @Query("DELETE FROM DbSectionDetails WHERE id = :detailsId")
    suspend fun deleteDetailsById(detailsId: Long)

    @Query("DELETE FROM DbSectionDetails WHERE sectionId = :sectionId")
    suspend fun deleteDetailsBySectionId(sectionId: Long)

    @Query("SELECT * FROM DbSectionDetails")
    suspend fun getDetails(): List<DbSectionDetails>
}