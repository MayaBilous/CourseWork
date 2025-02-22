package com.example.coursework.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.coursework.data.db.dao.SportSectionDao
import com.example.coursework.data.db.dao.LoginDao
import com.example.coursework.data.db.model.DbSportSection
import com.example.coursework.data.db.model.DbLogin

@Database([DbSportSection::class, DbLogin::class], version = 1)
abstract class SectionDataBase : RoomDatabase() {
    abstract fun LoginDao(): LoginDao
    abstract fun SportSectionDao(): SportSectionDao
}