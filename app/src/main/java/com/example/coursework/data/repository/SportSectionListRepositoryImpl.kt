package com.example.coursework.data.repository

import com.example.coursework.data.db.SectionDataBaseProvider
import com.example.coursework.data.repository.mapper.SportSectionMapper
import com.example.coursework.domain.boundary.SportSectionListRepository
import com.example.coursework.domain.entity.SportSection

class SportSectionListRepositoryImpl : SportSectionListRepository {

    private val mapper = SportSectionMapper()
    private val sportSectionDao = SectionDataBaseProvider.db.SportSectionDao()


    override suspend fun getSportSections(): List<SportSection> {
        return sportSectionDao.getSectionWithDetails().map { mapper.mapToDomain(it) }
    }

    override suspend fun getSportSectionDetails(sectionId: Long): SportSection? {
        return sportSectionDao.getSectionWithDetailsById(sectionId)?.let { mapper.mapToDomain(it) }
    }

    override suspend fun deleteDetails(detailsId: Long) {
        sportSectionDao.deleteDetailsById(detailsId)
    }

    override suspend fun delete(sportSection: SportSection) {
        sportSectionDao.delete(mapper.mapToDb(sportSection))
    }

    override suspend fun upsert(sportSection: SportSection) {
        val sectionId = sportSectionDao.updateSection(mapper.mapSectionToDb(sportSection))
            .let { newId ->
                sportSection.id ?: newId
            }
        val dbSectionDetails = sportSection.sectionDetails.map {
            mapper.mapDetailsToDb(it.copy(sectionId = sectionId))
        }
        sportSectionDao.updateDetails(dbSectionDetails)
    }
}

