package com.example.coursework.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.coursework.data.db.model.DbSportSection

@Dao
interface SportSectionDao {

    @Query("SELECT * FROM dbsportsection")
    fun getAll(): List<DbSportSection>

    @Query("SELECT * FROM dbsportsection WHERE id IN (:sportSectionId)")
    fun getById(sportSectionId: Long): DbSportSection

    @Update
    fun update(dbSection: DbSportSection)

    @Upsert
    fun insert(dbSection: DbSportSection): Long

    @Delete
    fun delete(dbSection: DbSportSection)
}