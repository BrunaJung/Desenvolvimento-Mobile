package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                CoroutinesDemoApp()
            }
        }
    }
}

@Composable
fun CoroutinesDemoApp() {
    val viewModel: MainViewModel = viewModel()
    val data = viewModel.data.value
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Dados --> $data")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.fetchData() }) {
            Text("Buscar Dados")
        }
    }
}

class MainViewModel : ViewModel() {
    private val _data = mutableStateOf("Sem dados")
    val data: State<String> get() = _data
    fun fetchData() { viewModelScope.launch {
        _data.value = fetchDataFromNetwork() }
    }
    private suspend fun fetchDataFromNetwork(): String {
        delay(5000L)
        return "Dados obtidos da rede"
    } }