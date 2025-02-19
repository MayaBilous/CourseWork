package com.example.coursework.domain.entity

data class SportSections(
    val id: Int,
    val name: String,
    val address: String,
    val workingDays: String,
    val phoneNumber: Int,
    ) {

    companion object {
        val default = SportSections(
            id = 0,
            name = "",
            address = "",
            workingDays = "",
            phoneNumber = 0,
        )
    }
}
