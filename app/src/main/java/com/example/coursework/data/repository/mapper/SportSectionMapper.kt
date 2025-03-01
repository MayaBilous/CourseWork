//package com.example.coursework.data.repository.mapper
//
//import com.example.coursework.data.db.model.DbLogin
//import com.example.coursework.data.db.model.DbSportSection
//import com.example.coursework.domain.entity.Login
//import com.example.coursework.domain.entity.SportSection
//
//class SportSectionMapper {
//    fun mapToDomain(dbSportSection: DbSportSection): SportSection {
//        return SportSection(
//            id = dbSportSection.id,
//            name = dbSportSection.sectionName,
//            district = dbSportSection.district,
//            address = dbSportSection.address,
//            workingDays = dbSportSection.workingDays,
//            phoneNumber = dbSportSection.phoneNumber,
//        )
//    }
//
//    fun domainToMap(sportSection: SportSection): DbSportSection {
//        return DbSportSection(
//            id = sportSection.id,
//            sectionName = sportSection.name,
//            district = sportSection.district,
//            address = sportSection.address,
//            workingDays = sportSection.workingDays,
//            phoneNumber = sportSection.phoneNumber,
//        )
//    }
//}