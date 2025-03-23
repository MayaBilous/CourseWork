package com.example.coursework.domain.usecase

import com.example.coursework.domain.entity.SectionDetails
import com.example.coursework.domain.entity.SportSection

interface CheckSectionDetails {

    suspend operator fun invoke(sportSection: SportSection): Boolean
}

class CheckSectionDetailsUseCase(
) : CheckSectionDetails {

    override suspend fun invoke(sportSection: SportSection): Boolean {
        val sectionDetails = sportSection.sectionDetails.first()
        if (sportSection.sectionName.isEmpty() ||
            sectionDetails.price <= 0 ||
            sectionDetails.address.isEmpty() ||
            sectionDetails.workingDays.isEmpty()||
            sectionDetails.phoneNumber.isEmpty()
        ) {
            return false
        } else {
            return true
        }
    }
}