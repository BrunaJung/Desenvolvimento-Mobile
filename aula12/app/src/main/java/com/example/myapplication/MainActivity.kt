package com.example.myapplication

import android.net.ConnectivityManager
import android.content.Context
import android.content.Intent
import android.net.NetworkCapabilities
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {
                    val viewModel: MainViewModel = viewModel()
                    val navController = rememberNavController()

                    AppNavHost(navController, viewModel)
                }
            }
        }
    }
}

@Composable
fun MyScreen() {
    val viewModel: MainViewModel = viewModel()
    val pessoa = viewModel.pessoa
}

@Composable
fun AppNavHost(navController: NavHostController, viewModel: MainViewModel) {

    NavHost(
        navController = navController,
        startDestination = "menu"
    ) {
        composable("menu") { MenuPrincipal(navController, viewModel) }
        composable("dados") { TelaDados(navController, viewModel) }
        composable("compartilha") { TelaCompartilha(navController, viewModel) }
    }
}

@Composable
fun TelaDados(navController: NavHostController, viewModel: MainViewModel)
{
    var nome by rememberSaveable { mutableStateOf(viewModel.pessoa?.nome ?: "") }
    var numeroTelefone by rememberSaveable { mutableStateOf(viewModel.pessoa?.numeroTelefone ?: "") }
    var descricao by rememberSaveable { mutableStateOf(viewModel.pessoa?.descricao ?: "") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)
        ,horizontalAlignment = Alignment.CenterHorizontally
        ,verticalArrangement = Arrangement.Center
    ) {
        Text("Nome:")
        OutlinedTextField(value = nome,onValueChange = { nome = it })

        Text("Telefone:")
        OutlinedTextField(value = numeroTelefone,onValueChange = { numeroTelefone = it })

        Text("Descrição:")
        OutlinedTextField(value = descricao,onValueChange = { descricao = it })

        Button(onClick = {val pessoa = Pessoa(nome, numeroTelefone, descricao)
            viewModel.setPessoa(pessoa) // Atualiza o ViewModel
            navController.popBackStack() // Voltar para a tela anterior
        }) {Text("Voltar")}
    }
}


@Composable
fun MenuPrincipal(navController: NavHostController ,viewModel : MainViewModel) {
    val pessoa = viewModel.pessoa

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        pessoa?.let {
            Text(text = it.nome)
            Text(text = it.numeroTelefone)
            Text(text = it.descricao)
        } ?: Text(text = "Não há dados pessoais")

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("dados") }) {
            Text("Dados")
        }
        if (pessoa?.nome?.isNotBlank() == true &&
            pessoa.numeroTelefone.isNotBlank() &&
            pessoa.descricao.isNotBlank()) {
            Button(
                onClick = {
                    navController.navigate("compartilha")
                }
            ) {
                Text("Compartilhamento")
            }
        }
    }
}

@Composable
fun TelaCompartilha(navController: NavHostController, viewModel: MainViewModel) {
    var pessoa = viewModel.pessoa
    val context = LocalContext.current
    var intention by remember { mutableStateOf("") }

    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val isConnect = remember {
        val activeNetwork = connectivityManager.activeNetwork
        activeNetwork != null && connectivityManager.getNetworkCapabilities(activeNetwork)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    val personalData = pessoa?.let {
        "Nome: ${it.nome} \nTelefone: ${it.numeroTelefone} \nDescrição: ${it.descricao}"
    } ?: "Nenhum dado pessoal disponível."

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Dados Pessoais Preenchidos:")
        Text(personalData)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Intenção:")
        TextField(value = intention, onValueChange = { intention = it })
        Spacer(modifier = Modifier.height(16.dp))

        if (intention.isNotBlank()) {
            if (isConnect) {
                Button(onClick = {
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "$personalData\nIntenção: $intention")
                        type = "text/plain"
                    }
                    context.startActivity(Intent.createChooser(intent, "Compartilhe"))
                }) {
                    Text("Compartilhar")
                }
            } else {
                Text("Falha de compartilhamento! Confira a sua conexão com a rede.")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("Voltar")
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
    MyApplicationTheme {
        Greeting("Android")
    }
}