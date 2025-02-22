package com.example.coursework.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.coursework.data.db.model.DbLogin

@Dao
interface LoginDao {

    @Query("SELECT * FROM dblogin")
    fun getAll(): List<DbLogin>

    @Insert
    fun insert(dbLogin: DbLogin): Long
}
