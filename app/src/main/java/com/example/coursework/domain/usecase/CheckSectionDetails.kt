package com.example.coursework.domain.usecase

import com.example.coursework.domain.entity.SectionDetails
import com.example.coursework.domain.entity.SportSection

interface CheckSectionDetails {

    suspend operator fun invoke(sectionDetails: SectionDetails): Boolean
}

class CheckSectionDetailsUseCase(
) : CheckSectionDetails {

    override suspend fun invoke(sectionDetails: SectionDetails): Boolean {
        if (sectionDetails.price <= 0 ||
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