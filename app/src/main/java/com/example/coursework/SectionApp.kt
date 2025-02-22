package com.example.coursework

import android.app.Application
import com.example.coursework.data.db.SectionDataBaseProvider

class SectionApp() : Application() {
    override fun onCreate() {
        super.onCreate()
        SectionDataBaseProvider.init(this)
    }
}