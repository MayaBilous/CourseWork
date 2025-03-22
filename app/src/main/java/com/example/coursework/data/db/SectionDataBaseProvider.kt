package com.example.coursework.data.db

import android.content.Context
import androidx.room.Room

object SectionDataBaseProvider {
    lateinit var db: SectionDataBase
    fun init(applicationContext: Context) {
        db = Room.databaseBuilder(
            applicationContext,
            SectionDataBase::class.java, "database-name"
        ).fallbackToDestructiveMigration()
            .build()
    }
}