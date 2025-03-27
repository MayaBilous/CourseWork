package com.example.coursework.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.coursework.data.db.model.DbSectionDetails

@Dao
interface SectionDetailsDao {

    @Upsert
    abstract suspend fun updateDetails(dbSectionDetails: List<DbSectionDetails>)

    @Query("DELETE FROM DbSectionDetails WHERE id = :detailsId")
    abstract suspend fun deleteDetailsById(detailsId: Long)
}