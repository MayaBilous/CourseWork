package com.example.coursework.data.repository.mapper

import com.example.coursework.data.db.model.DbSportSection
import com.example.coursework.domain.entity.SportSection

class SportSectionMapper {
    fun mapToDomain(dbSportSection: DbSportSection): SportSection {
        return SportSection(
            id = dbSportSection.id,
            sectionName = dbSportSection.sectionName,
            address = dbSportSection.address,
            workingDays = dbSportSection.workingDays,
            phoneNumber = dbSportSection.phoneNumber,
            price = dbSportSection.price,
        )
    }

    fun domainToMap(sportSection: SportSection): DbSportSection {
        return DbSportSection(
            id = sportSection.id,
            sectionName = sportSection.sectionName,
            address = sportSection.address,
            workingDays = sportSection.workingDays,
            phoneNumber = sportSection.phoneNumber,
            price = sportSection.price,
        )
    }
}