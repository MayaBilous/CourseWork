package com.example.coursework.domain.usecase

import com.example.coursework.domain.boundary.SportSectionListRepository
import com.example.coursework.domain.entity.SportSection


interface GetSectionDetails {

    suspend operator fun invoke(sectionDetailsId: Long): SportSection
}

class GetSectionDetailsUseCase(
    private val sportSectionListRepository: SportSectionListRepository
) : GetSectionDetails {

    override suspend fun invoke(sectionDetailsId: Long): SportSection {
        return sportSectionListRepository.getSportSectionDetails(sectionDetailsId)!!
    }
}
