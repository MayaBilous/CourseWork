package com.example.coursework

import android.app.Application
import com.example.coursework.data.db.SectionDataBaseProvider
import com.example.coursework.data.db.model.DbLogin
import com.example.coursework.data.db.model.DbSportSection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class SectionApp() : Application() {
    override fun onCreate() {
        super.onCreate()
        SectionDataBaseProvider.init(this)
        val db = SectionDataBaseProvider.db
        val coroutineScope = CoroutineScope(SupervisorJob())
        coroutineScope.launch {
            if (db.LoginDao().getAll().isEmpty()) {
                db.LoginDao().insert(
                    DbLogin(id = null, userName = "admin", password = "123", isAdmin = true),
                    DbLogin(id = null, userName = "user", password = "321", isAdmin = false),
                )
            }
            if (db.SportSectionDao().getAll().isEmpty()){
                db.SportSectionDao().insert(
                    DbSportSection(id = null, sectionName = "Футбол", district = "Троєщина", address = "вулиця Миколи Лаврухіна, 4", workingDays = "Середа, Субота, Неділя", phoneNumber = "+380504723987"),
                    DbSportSection(id = null, sectionName = "Баскетбол", district = "Солом`янський", address = "проспект Любомира Гузара, 3", workingDays = "Понеділок, Середа, П`ятниця", phoneNumber = "+380504784639"),
                    DbSportSection(id = null, sectionName = "Хокей", district = "Святошинський", address = "вулиця Салютна, 2", workingDays = "Вівторок, Четвер, Субота", phoneNumber = "+380501144622"),
                )
            }
        }
    }
}