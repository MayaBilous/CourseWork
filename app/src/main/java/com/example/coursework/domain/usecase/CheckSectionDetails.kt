package com.example.coursework.domain.usecase

import com.example.coursework.domain.entity.SportSection

interface CheckSectionDetails {

    suspend operator fun invoke(sportSection: SportSection): Boolean
}

class CheckSectionDetailsUseCase(
) : CheckSectionDetails {

    override suspend fun invoke(sportSection: SportSection): Boolean {
        if (sportSection.sectionName.isEmpty() ||
            sportSection.district.isEmpty() ||
            sportSection.address.isEmpty() ||
            sportSection.workingDays.isEmpty()||
            sportSection.phoneNumber.isEmpty()
        ) {
            return false
        } else {
            return true
        }
    }
}