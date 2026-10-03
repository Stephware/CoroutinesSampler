package com.example.coroutinessampler

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.data.Student
import com.example.coroutinessampler.ui.StudentUIState
import com.example.coroutinessampler.ui.StudentViewModel
import com.example.coroutinessampler.ui.theme.CoroutinesSamplerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoroutinesSamplerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    StudentScreen(modifier = Modifier.fillMaxSize().padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CoroutinesSamplerTheme {
        Greeting("Android")
    }
}

@Composable
fun StudentScreen(viewModel: StudentViewModel = viewModel(), modifier: Modifier){

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        Text(
            text = "Student directory",
            style = MaterialTheme.typography.headlineMedium
        )

        when(val state = uiState){
            StudentUIState.loading -> {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Text("Loading students")
                }
            }
            is StudentUIState.Success-> {
                StudentList(state.students)
                Button(
                    onClick = {
                        viewModel.loadStudents()
                    }
                ) {
                    Text("Reload")
                }
            }
            is StudentUIState.Error ->{
                Text(
                    text = "Error: ${state.message}"
                )
                Button(
                    onClick = {
                        viewModel.loadStudents()
                    }
                ) {
                    Text("Retry")
                }
            }
        }
    }
}

@Composable
fun StudentList(students: List<Student>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(students) { student ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = student.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = student.course
                )
                Text(
                    text = "Year ${student.yearLevel}"
                )
            }
        }
    }
}