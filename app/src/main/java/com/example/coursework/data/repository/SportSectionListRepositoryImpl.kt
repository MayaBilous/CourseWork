package com.example.coursework.data.repository

import com.example.coursework.data.db.SectionDataBaseProvider
import com.example.coursework.data.repository.mapper.SportSectionMapper
import com.example.coursework.domain.boundary.SportSectionListRepository
import com.example.coursework.domain.entity.SportSection

class SportSectionListRepositoryImpl : SportSectionListRepository {

    private val mapper = SportSectionMapper()
    private val sportSectionDao = SectionDataBaseProvider.db.SportSectionDao()


    override suspend fun getSportSection(): List<SportSection> {
        return sportSectionDao.getAll().map { mapper.mapToDomain(it) }
    }

    override suspend fun delete(sectionId: Long) {
        sportSectionDao.deleteById(sectionId)
    }
    override suspend fun update(sportSection: SportSection) {
        sportSectionDao.update(mapper.domainToMap(sportSection))
    }

    override suspend fun insert(sportSection: SportSection) {
        sportSectionDao.insert(mapper.domainToMap(sportSection))
    }
}

