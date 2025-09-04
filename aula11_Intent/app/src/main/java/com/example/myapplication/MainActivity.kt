package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                    //AbrirSiteButton()
                    BasicoDropdownMenu()
            }
        }
    }
}

@Composable
fun AbrirSiteButton(){
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Button(onClick = {
            val intent = Intent(Intent.ACTION_VIEW,
                Uri.parse("https://developer.android.com"))
            context.startActivity(intent)
        }) {
            Text("Abrir Site")
        }
    }
}

@Composable
fun BasicoDropdownMenu(){
    var expandido by remember { mutableStateOf(false) }
    var selOpc by remember {mutableStateOf("MENU")}

    Box{ Button(onClick = {expandido = !expandido}){
            Text(selOpc)
        }
        DropdownMenu(expanded = expandido,
            onDismissRequest = {expandido = false}) {
            DropdownMenuItem(text = {Text("Dados")},
                onClick = {selOpc = "Dados"
                expandido = false}
            )

            DropdownMenuItem(text = {Text("Compartilhamento")},
                onClick = {selOpc = "Compartilhamento"
                    expandido = false}
            )
        }

    }
}

@Composable

