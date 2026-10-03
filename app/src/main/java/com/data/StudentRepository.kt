package com.data

import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class StudentRepository {
    suspend fun getStudents(): List<Student>{

        delay(2000.milliseconds)

        return listOf(
            Student(
                1,
                "Mark",
                "BSCpE",
                4
            ),
            Student(
                2,
                "Kurt",
                "BSCpE",
                4
            ),
            Student(
                3,
                "Stephen",
                "BSCpE",
                4
            )
        )
    }
}