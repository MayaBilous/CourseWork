package com.example.coursework.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.coursework.data.db.model.DbSection

@Dao
interface SectionDao {

    @Query("SELECT * FROM dbsection")
    fun getAll(): List<DbSection>

    @Query("SELECT * FROM dbsection WHERE id IN (:sectionId)")
    fun loadById(sectionId: Int): DbSection

    @Update
    fun updateSection(vararg dbSection: DbSection)

    @Insert
    fun insert(vararg dbSection: DbSection)

    @Delete
    fun delete(dbSection: DbSection)
}