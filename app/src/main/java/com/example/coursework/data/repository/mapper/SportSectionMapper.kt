package com.example.coursework.data.repository.mapper

import com.example.coursework.data.db.model.DbSectionDetails
import com.example.coursework.data.db.model.DbSectionWithDetails
import com.example.coursework.data.db.model.DbSportSection
import com.example.coursework.domain.entity.SectionDetails
import com.example.coursework.domain.entity.SportSection

class SportSectionMapper {

    fun mapToDomain(
        dbSectionWithDetails: DbSectionWithDetails
    ): SportSection {
        return SportSection(
            id = dbSectionWithDetails.sportSection.id,
            sectionName = dbSectionWithDetails.sportSection.sectionName,
            sectionDetails = dbSectionWithDetails.sectionDetails.map {
                SectionDetails(
                    detailsId = it.id,
                    sectionId = it.sectionId,
                    address = it.address,
                    workingDays = it.workingDays,
                    phoneNumber = it.phoneNumber,
                    price = it.price,
                    isSelected = it.isSelected,
                )
            }
        )
    }

    fun mapToDb(sportSection: SportSection): DbSectionWithDetails {
        return DbSectionWithDetails(
            sportSection = DbSportSection(
                id = sportSection.id,
                sectionName = sportSection.sectionName
            ),
            sectionDetails = sportSection.sectionDetails.map {
                DbSectionDetails(
                    id = it.detailsId,
                    sectionId = it.sectionId,
                    address = it.address,
                    workingDays = it.workingDays,
                    phoneNumber = it.phoneNumber,
                    price = it.price,
                    isSelected = it.isSelected,
                )
            }
        )
    }

    fun mapSectionToDb(sportSection: SportSection): DbSportSection {
        return DbSportSection(
            id = sportSection.id,
            sectionName = sportSection.sectionName
        )
    }

    fun mapDetailsToDb(sectionDetails: SectionDetails): DbSectionDetails {
        return DbSectionDetails(
            id = sectionDetails.detailsId,
            sectionId = sectionDetails.sectionId,
            address = sectionDetails.address,
            workingDays = sectionDetails.workingDays,
            phoneNumber = sectionDetails.phoneNumber,
            price = sectionDetails.price,
            isSelected = sectionDetails.isSelected,
        )
    }


    fun mapToDetailsList(
        dbSectionDetails: List<DbSectionDetails>
    ): List<SectionDetails> {
        return dbSectionDetails.map { dbSectionDetails ->
            SectionDetails(
                        detailsId = dbSectionDetails.id,
                        sectionId = dbSectionDetails.sectionId,
                        address = dbSectionDetails.address,
                        workingDays = dbSectionDetails.workingDays,
                        phoneNumber = dbSectionDetails.phoneNumber,
                        price = dbSectionDetails.price,
                        isSelected = dbSectionDetails.isSelected,
            )
        }
    }
}