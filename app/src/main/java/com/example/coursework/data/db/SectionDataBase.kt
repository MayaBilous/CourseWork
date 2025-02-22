package com.example.coursework.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.coursework.data.db.dao.SectionDao
import com.example.coursework.data.db.dao.UserDao
import com.example.coursework.data.db.model.DbSection
import com.example.coursework.data.db.model.DbUser

@Database([DbSection::class, DbUser::class], version = 1)
abstract class SectionDataBase : RoomDatabase() {
    abstract fun UserDao(): UserDao
    abstract fun SectionDao(): SectionDao
}