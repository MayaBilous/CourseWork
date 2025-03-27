package com.example.coursework.data.repository

import com.example.coursework.data.db.SectionDataBaseProvider
import com.example.coursework.data.repository.mapper.SportSectionMapper
import com.example.coursework.domain.boundary.SportSectionListRepository
import com.example.coursework.domain.entity.SportSection

class SportSectionListRepositoryImpl : SportSectionListRepository {

    private val mapper = SportSectionMapper()
    private val sportSectionDao = SectionDataBaseProvider.db.SportSectionDao()
    private val sectionDetailsDao = SectionDataBaseProvider.db.SectionDetailsDao()


    override suspend fun getSportSections(): List<SportSection> {
        return sportSectionDao.getSectionWithDetails().map { mapper.mapToDomain(it) }
    }

    override suspend fun getSportSectionDetails(sectionId: Long): SportSection? {
        return sportSectionDao.getSectionWithDetailsById(sectionId)?.let { mapper.mapToDomain(it) }
    }

    override suspend fun deleteDetails(detailsId: Long) {
        sectionDetailsDao.deleteDetailsById(detailsId)
    }

    override suspend fun delete(sportSection: SportSection) {
        sportSectionDao.deleteSectionById(sportSection.id ?:0)
        sportSection.sectionDetails.forEach {
            sectionDetailsDao.deleteDetailsById(it.detailsId ?:0)
        }
        sportSectionDao.delete(mapper.mapToDb(sportSection))
    }

    override suspend fun upsert(sportSection: SportSection) {
        val foundSectionId = sportSection.id
            ?: sportSectionDao.findSectionByName(sportSection.sectionName)?.sportSection?.id

        val sectionIdForDetails = foundSectionId?.let {
            sportSectionDao.updateSection(mapper.mapSectionToDb(sportSection.copy(id = it)))
            it
        } ?: sportSectionDao.updateSection(mapper.mapSectionToDb(sportSection))

        val dbSectionDetails = sportSection.sectionDetails.map {
            mapper.mapDetailsToDb(it.copy(sectionId = sectionIdForDetails))
        }
        sectionDetailsDao.updateDetails(dbSectionDetails)
    }
}

