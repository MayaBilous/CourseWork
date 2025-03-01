package com.example.coursework.domain.boundary

import com.example.coursework.domain.entity.SportSection


interface SportSectionListRepository {
    suspend fun getSportSection(): List<SportSection>
    suspend fun delete(sectionId: Long)
    suspend fun update(sportSection: SportSection)
}
