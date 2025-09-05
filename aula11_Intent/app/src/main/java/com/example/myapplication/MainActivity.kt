package com.example.myapplication

import ads_mobile_sdk.h6
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
fun AppPrincipal() {
    var selOpc by remember { mutableStateOf("MENU") }

    Column {
        BasicoDropdownMenu(selOpc) { novaOpcao ->
            selOpc = novaOpcao
        }

        when (selOpc) {
            "Dados" -> TelaDados()
            "Compartilhamento" -> TelaCompartilhamento()
        }
    }
}

@Composable
fun BasicoDropdownMenu(opcaoSelecionada: String, selOpc: (String) -> Unit) {
    var expandido by remember { mutableStateOf(false) }
    var selOpc by remember {mutableStateOf("MENU")}

    Box{ Button(onClick = {expandido = !expandido}){
            Text(opcaoSelecionada)
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
fun TelaDados() {
    var nome by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var biotipo by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Insira seus dados", style = MaterialTheme.typography.h6)

        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = telefone,
            onValueChange = { telefone = it },
            label = { Text("Telefone") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = biotipo,
            onValueChange = { biotipo = it },
            label = { Text("Biotipo") },
            modifier = Modifier.fillMaxWidth()
        )


    }
}

@Composable
fun TelaCompartilhamento(){

}

