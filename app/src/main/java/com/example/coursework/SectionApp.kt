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
                    DbSportSection(id = null, sectionName = "Футбол", address = "вулиця Миколи Лаврухіна, 4", workingDays = "Середа, Субота, Неділя", phoneNumber = "+380504723987", price = 300),
                    DbSportSection(id = null, sectionName = "Футбол", address = "вулиця Миколи, 10", workingDays = "Середа, Неділя", phoneNumber = "+380505386487", price = 250),
                    DbSportSection(id = null, sectionName = "Баскетбол", address = "проспект Любомира, 10", workingDays = "Понеділок, Середа, П`ятниця", phoneNumber = "+380509417639", price = 500),
                    DbSportSection(id = null, sectionName = "Баскетбол", address = "проспект Любомира Гузара, 3", workingDays = "Понеділок, Середа", phoneNumber = "+380091647639", price = 300),
                    DbSportSection(id = null, sectionName = "Баскетбол", address = "проспект Перемоги, 7", workingDays = "Середа, П`ятниця", phoneNumber = "+380504946239", price = 450),
                    DbSportSection(id = null, sectionName = "Хокей",  address = "вулиця Салютна, 2", workingDays = "Вівторок, Четвер, Субота", phoneNumber = "+380501144622", price = 400),
                )
            }
        }
    }
}