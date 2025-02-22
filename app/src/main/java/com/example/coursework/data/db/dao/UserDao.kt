package com.example.coursework.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.coursework.data.db.model.DbUser

@Dao
interface UserDao {

    @Query("SELECT * FROM dbuser")
    fun getAll(): List<DbUser>
}
