package com.example.coursework.domain.entity

data class SportSection(
    val id: Long?,
    val sectionName: String,
    val district: String,
    val address: String,
    val workingDays: String,
    val phoneNumber: String,
    ) {

    companion object {
        val default = SportSection(
            id = 0,
            sectionName = "",
            district = "",
            address = "",
            workingDays = "",
            phoneNumber = "",
        )
    }
}
