//package com.example.coursework.data.repository
//
//import com.example.coursework.data.db.SectionDataBaseProvider
//import com.example.coursework.data.db.model.DbSportSection
//import com.example.coursework.data.repository.mapper.LoginMapper
//import com.example.coursework.data.repository.mapper.SportSectionMapper
//import com.example.coursework.domain.boundary.SportSectionListRepository
//import com.example.coursework.domain.entity.Login
//import com.example.coursework.domain.entity.SportSection
//
//class SportSectionListRepositoryImpl : SportSectionListRepository {
//
//    private val mapper = SportSectionMapper()
//    private val sportSectionDao = SectionDataBaseProvider.db.SportSectionDao()
//
//
//    override suspend fun getSportSection(): List<SportSection> {
//        return sportSectionDao.getAll().map { mapper.mapToDomain(it) }
//    }
//
//    override suspend fun getDbSportSection(): List<DbSportSection> {
//        TODO("Not yet implemented")
//    }
//}

package com.example.coursework.data.repository

import com.example.coursework.domain.boundary.SportSectionListRepository
import com.example.coursework.domain.entity.SportSection

class SportSectionListRepositoryImpl : SportSectionListRepository {

    override suspend fun getList(): MutableList<SportSection> {
        return mutableListOf(
            SportSection(id = 1, name = "Football", address = "aaa", workingDays = "Monday", phoneNumber = "123"),
            SportSection(id = 2, name = "Basketball", address = "bbb", workingDays = "Friday", phoneNumber = "345"),
            SportSection(id = 3, name = "Hokey", address = "ccc", workingDays = "Sunday", phoneNumber = "657"),
        )
    }
}