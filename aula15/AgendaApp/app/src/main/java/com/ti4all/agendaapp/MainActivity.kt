package com.ti4all.agendaapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ti4all.agendaapp.data.Agenda
import com.ti4all.agendaapp.data.AgendaViewModel
import com.ti4all.agendaapp.ui.theme.AgendaAppTheme
import com.ti4all.agendaapp.ui.theme.EditarContatoScreen
import com.ti4all.agendaapp.ui.theme.ListaContatosScreen

class MainActivity : ComponentActivity() {
    private val viewModel: AgendaViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AgendaAppTheme {
                AgendaScreen(viewModel = viewModel)
            }
        }
        setContent {
            AgendaAppTheme {
                var contatoSelecionado by remember { mutableStateOf<Agenda?>(null) }
                var showDialog by remember { mutableStateOf(false) }

                Scaffold(
                    topBar = {
                        TopAppBar(title = { Text("Agenda Telefônica") })
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = { showDialog = true }) {
                            Icon(Icons.Filled.Add, contentDescription = "Adicionar contato")
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        if (contatoSelecionado == null) {
                            ListaContatosScreen(
                                viewModel = viewModel,
                                onEditar = { contato -> contatoSelecionado = contato }
                            )
                        } else {
                            EditarContatoScreen(
                                viewModel = viewModel,
                                contatoId = contatoSelecionado!!.id,
                                onVoltar = { contatoSelecionado = null }
                            )
                        }
                    }
                }

                if (showDialog) {
                    AgendaFormDialog(
                        onDismissRequest = { showDialog = false }
                    ) { nome, telefone ->
                        viewModel.inserir(Agenda(nome = nome, telefone = telefone))
                        showDialog = false
                    }
                }
            }
        }
    }

    @Composable
    fun AgendaList(agenda: Agenda) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Nome: ${agenda.nome}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Telefone: ${agenda.telefone}")
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AgendaScreen(viewModel: AgendaViewModel) {
        val agendaList by viewModel.agendaList.collectAsState()
        var showDialog by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            viewModel.listarTodos()
        }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Agenda Telefônica") })
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showDialog = true }
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Adicionar contato")
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                agendaList.forEach { agenda ->
                    AgendaList(agenda = agenda)
                }
            }
            if (showDialog) {
                AgendaFormDialog(
                    onDismissRequest = { showDialog = false }
                ) { nome, telefone ->
                    viewModel.inserir(Agenda(nome = nome, telefone = telefone))
                    showDialog = false
                }
            }
        }
    }

    @Composable
    fun AgendaFormDialog(
        onDismissRequest: () -> Unit,
        onAddClick: (String, String) -> Unit
    ) {
        Dialog(onDismissRequest = onDismissRequest) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.background,
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    var nome by remember { mutableStateOf("") }
                    var telefone by remember { mutableStateOf("") }

                    OutlinedTextField(
                        value = nome,
                        onValueChange = { nome = it },
                        label = { Text("Nome") }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = telefone,
                        onValueChange = { telefone = it },
                        label = { Text("Telefone") }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            onAddClick(nome, telefone)
                            onDismissRequest()
                        }
                    ) {
                        Text("Adicionar contato")
                    }
                }
            }
        }
    }
}