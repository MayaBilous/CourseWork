package com.example.coursework

import android.app.Application
import com.example.coursework.data.db.SectionDataBaseProvider
import com.example.coursework.data.db.model.DbLogin
import com.example.coursework.data.db.model.DbSectionDetails
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
            if (db.SportSectionDao().getSectionWithDetails().isEmpty()) {
                val sportSection1 = DbSportSection(id = 1, sectionName = "Футбол")
                val sportSection2 = DbSportSection(id = 2, sectionName = "Баскетбол")
                val sportSection3 = DbSportSection(id = 3, sectionName = "Волейбол")
                val sportSection4 = DbSportSection(id = 4, sectionName = "Теніс")

                db.SportSectionDao().updateSection(sportSection1)
                db.SportSectionDao().updateSection(sportSection2)
                db.SportSectionDao().updateSection(sportSection3)
                db.SportSectionDao().updateSection(sportSection4)

                val sectionDetailsList = listOf(
                    DbSectionDetails(
                        id = 1,
                        sectionId = 1,
                        address = "вул. Спортивна, 10",
                        workingDays = "Пн-Пт, 9:00-18:00",
                        phoneNumber = "0501234567",
                        price = 500
                    ),
                    DbSectionDetails(
                        id = 2,
                        sectionId = 2,
                        address = "вул. Баскетбольна, 15",
                        workingDays = "Пн-Пт, 10:00-20:00",
                        phoneNumber = "0509876543",
                        price = 400
                    ),
                    DbSectionDetails(
                        id = 3,
                        sectionId = 3,
                        address = "вул. Волейбольна, 20",
                        workingDays = "Пн-Сб, 8:00-17:00",
                        phoneNumber = "0631122334",
                        price = 450
                    ),
                    DbSectionDetails(
                        id = 4,
                        sectionId = 4,
                        address = "вул. Тенісна, 8",
                        workingDays = "Пн-Пт, 9:00-19:00",
                        phoneNumber = "0662233445",
                        price = 600
                    ),
                    DbSectionDetails(
                        id = 6,
                        sectionId = 2,
                        address = "вул. Баскетбольна, 18",
                        workingDays = "Пн-Пт, 9:00-18:00",
                        phoneNumber = "0509876543",
                        price = 420
                    )
                )
                db.SportSectionDao().updateDetails(sectionDetailsList)
            }
        }
    }
}