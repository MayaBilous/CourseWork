package com.example.coursework.domain.entity

data class SportSection(
    val id: Long?,
    val sectionName: String,
    val address: String,
    val workingDays: String,
    val phoneNumber: String,
    val price: Int,
    ) {

    companion object {
        val default = SportSection(
            id = 0,
            sectionName = "",
            address = "",
            workingDays = "",
            phoneNumber = "",
            price = 0,
        )
    }
}
