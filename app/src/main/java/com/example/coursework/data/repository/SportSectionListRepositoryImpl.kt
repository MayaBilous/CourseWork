package com.example.coursework.data.repository

import com.example.coursework.domain.boundary.SportSectionListRepository
import com.example.coursework.domain.entity.SportSection

class SportSectionListRepositoryImpl : SportSectionListRepository {

    override suspend fun getList(): MutableList<SportSection> {
        return mutableListOf(
            SportSection(id = 1, name = "Football", address = "aaa", workingDays = "Monday", phoneNumber = 123),
            SportSection(id = 2, name = "Basketball", address = "bbb", workingDays = "Friday", phoneNumber = 345),
            SportSection(id = 3, name = "Hokey", address = "ccc", workingDays = "Sunday", phoneNumber = 657),
        )
    }
}