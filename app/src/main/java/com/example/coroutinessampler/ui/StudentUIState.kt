package com.example.coroutinessampler.ui

import com.data.Student

sealed interface StudentUIState {
    data object loading: StudentUIState

    data class Success(
        val students: List<Student>
    ): StudentUIState

    data class Error(
        val message: String
    ): StudentUIState
}