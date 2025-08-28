package com.example.aula9

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.aula9.ui.theme.Aula9Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Aula9Theme {
                Surface {
                    LoggerCicloVida()
                    Greeting("Maurício")
                }
            }
        }
    }
}

@Composable
fun Initial_Ui() {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> println("A activity está começando a ser visível")
                Lifecycle.Event.ON_STOP -> println("A activity não está mais visível")
                else -> println("Outros eventos: $event")
            }
        }
        val lifecycle = lifecycleOwner.lifecycle
        lifecycle.addObserver(observer)
        onDispose { lifecycle.removeObserver(observer) }
    }
}

@Composable
fun LoggerCicloVida() {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> Log.d("LoggerCicloVida", "onCreate chamado")
                Lifecycle.Event.ON_START -> Log.d("LoggerCicloVida", "onStart chamado")
                Lifecycle.Event.ON_RESUME -> Log.d("LoggerCicloVida", "onResume chamado")
                Lifecycle.Event.ON_PAUSE -> Log.d("LoggerCicloVida", "onPause chamado")
                Lifecycle.Event.ON_STOP -> Log.d("LoggerCicloVida", "onStop chamado")
                Lifecycle.Event.ON_DESTROY -> Log.d("LoggerCicloVida", "onDestroy chamado")
                else -> Log.d("LoggerCicloVida", "Outro evento: $event")
            }
        }
        val lifecycle = lifecycleOwner.lifecycle
        lifecycle.addObserver(observer)
        onDispose { lifecycle.removeObserver(observer) }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    /* val context = LocalContext.current
    val windowManager = context.getSystemService(WindowManager::class.java)
    val rotation = windowManager?.defaultDisplay?.rotation // pega a rotação exata da tela

    val backgroundImage = when (rotation) {
        Surface.ROTATION_0 -> painterResource(id = R.drawable.bg_vertical)      // em pé normal
        Surface.ROTATION_180 -> painterResource(id = R.drawable.bg_vertical_invertido) // em pé invertido
        Surface.ROTATION_90 -> painterResource(id = R.drawable.bg_horizontal)   // deitado normal
        Surface.ROTATION_270 -> painterResource(id = R.drawable.bg_horizontal_invertido) // deitado invertido
        else -> painterResource(id = R.drawable.bg_vertical)
    }*/

    val configuration = LocalConfiguration.current

    val backgroundImage = if (configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT) {
        painterResource(id = R.drawable.bg_vertical)
    } else {
        painterResource(id = R.drawable.bg_horizontal)
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = backgroundImage,
            contentDescription = "Imagem de fundo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Hello $name!",
                modifier = modifier
            )
            Text(
                text = "Acompanhe o Logcat!",
                modifier = modifier
            )
        }
    }
}
