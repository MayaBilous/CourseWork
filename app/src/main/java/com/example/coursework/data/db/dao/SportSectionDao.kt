package com.example.coursework.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.coursework.data.db.model.DbSportSection

@Dao
interface SportSectionDao {

    @Query("SELECT * FROM dbsportsection")
    suspend fun getAll(): List<DbSportSection>

    @Update
    suspend fun update(dbSection: DbSportSection)

    @Insert
    suspend fun insert(vararg dbSection: DbSportSection)

    @Query("DELETE FROM DbSportSection WHERE id = :sportSectionId")
    suspend fun deleteById(sportSectionId: Long)
}