package com.example.aula10


import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Switch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import com.example.aula10.ui.theme.Aula10Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Aula10Theme {
                NavegacaoTelas()
                    // NetworkStatus()
                    // Lanterna()

                /*
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding)
                        )
                    }

                 */
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun Lanterna() {
    var isChecked by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Switch(checked = isChecked,
            onCheckedChange = { isChecked = it},
            modifier = Modifier.fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
        if (isChecked) {
            LanternaOnOff(state = true)
        } else {
            LanternaOnOff(state = false)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.M)
@Composable
private fun LanternaOnOff(state: Boolean)  {
    val context = LocalContext.current
    val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    try {
        // Retorna a id da câmera a ser utilizada
        // [0 - câmera traseira] [1 - câmera frontal]
        val idCamera: String = cameraManager.cameraIdList[0]
        // Ativa e desativa a câmera
        cameraManager.setTorchMode(idCamera, state)
    } catch (e: Exception) {
        Log.e("erroCamera"
            , "Erro ao ligar/desligar a lanterna: ${e.message}", e)
        e.printStackTrace()
    }
}


@Composable
fun NavegacaoTelas() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "tela1"
    ) {
        composable("tela1") { Tela1(navController) }
        composable("tela2") { Tela2(navController) }
        composable("tela3") { Tela3(navController) }
    }
}


@Composable
fun Tela1(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { navController.navigate("tela2") }) {
            Text("Ir para Tela 2")
        }
    }
}


@Composable
fun Tela2(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Você está na Tela 2")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate("tela3") }) {
                Text("Ir para Tela 3")
            }
        }
    }
}

@Composable
fun Tela3(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Você está na Tela 3")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate("tela1") }) {
                Text("Voltar para Tela 1")
            }
        }
    }
}

@Composable
fun NetworkStatus() {
    val context = LocalContext.current
    val connectivityManager = remember {
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
    val isConnected = remember {
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column() {
            Text(text = "Internet: ${if (isConnected) "Ativa" else "Ausente"}"
                , fontSize = 32.sp)
        }
    }
}