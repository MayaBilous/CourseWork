package com.example.coursework.domain.boundary

import com.example.coursework.data.db.model.DbSportSection
import com.example.coursework.domain.entity.SectionDetails
import com.example.coursework.domain.entity.SportSection


interface SportSectionListRepository {

    suspend fun getSportSections(): List<SportSection>

    suspend fun getSportSectionDetails(sectionId: Long): SportSection?

    suspend fun deleteDetails(detailsId: Long)

    suspend fun delete(sportSection: SportSection)

    suspend fun upsert(sportSection: SportSection)

}
