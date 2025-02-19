package com.example.coursework.domain.usecase

import com.example.coursework.domain.boundary.SportSectionListRepository
import com.example.coursework.domain.entity.SportSections

interface DownloadSectionList {

    suspend operator fun invoke(): List<SportSections>
}

class DownloadSectionListUseCase(
    private val sportSectionListRepository: SportSectionListRepository
) : DownloadSectionList {

    override suspend fun invoke() :List<SportSections> {
        return sportSectionListRepository.getList()
    }
}
