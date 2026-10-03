package com.example.coroutinessampler.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.StudentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StudentViewModel(private val repository: StudentRepository = StudentRepository()) : ViewModel() {

    private val _uiState = MutableStateFlow<StudentUIState>(
        StudentUIState.loading
    )

    val uiState: StateFlow<StudentUIState> = _uiState.asStateFlow()

    init{
        //TODO: LOAD STUDENTS
        loadStudents()
    }

    fun loadStudents(){
        viewModelScope.launch {
            _uiState.value = StudentUIState.loading

            try{

                val students = repository.getStudents()

//                throw Exception("Testing Error")

                _uiState.value = StudentUIState.Success(students)

            }catch (e: Exception){
                _uiState.value = StudentUIState.Error(
                    e.message ?: "Unknown error"
                )
            }
        }
    }

}