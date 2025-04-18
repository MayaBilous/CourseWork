package com.example.coursework.domain.usecase

import com.example.coursework.domain.entity.SportSection

interface CheckSectionName {

    suspend operator fun invoke(sportSection: SportSection): Boolean
}

class CheckSectionNameUseCase(
) : CheckSectionName {

    override suspend fun invoke(sportSection: SportSection): Boolean {
        val sectionDetails = sportSection
        if (sectionDetails.sectionName.isEmpty()) {
            return false
        } else {
            return true
        }
    }
}