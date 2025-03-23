//package com.example.coursework.domain.usecase
//
//import com.example.coursework.domain.boundary.SportSectionListRepository
//import com.example.coursework.domain.entity.SportSection
//
//interface GetListByName {
//
//    suspend operator fun invoke(sectionName: String): SportSection?
//}
//
//class GetListByNameUseCase(
//    private val sportSectionListRepository: SportSectionListRepository
//) : GetListByName {
//    override suspend fun invoke(sectionName: String): SportSection? {
//        return sportSectionListRepository.findSectionByName(sectionName)
//    }
//}