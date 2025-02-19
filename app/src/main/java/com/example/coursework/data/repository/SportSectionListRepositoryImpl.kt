package com.example.coursework.data.repository

import com.example.coursework.domain.boundary.SportSectionListRepository
import com.example.coursework.domain.entity.SportSections

class SportSectionListRepositoryImpl : SportSectionListRepository {

    override suspend fun getList(): List<SportSections> {
        return listOf(
            SportSections(id = 1, name = "Football", address = "aaa", workingDays = "Monday", phoneNumber = 123),
            SportSections(id = 2, name = "Basketball", address = "bbb", workingDays = "Friday", phoneNumber = 345),
            SportSections(id = 3, name = "Hokey", address = "ccc", workingDays = "Sunday", phoneNumber = 657),
        )
    }
}