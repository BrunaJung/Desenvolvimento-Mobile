package com.ti4all.agendaapp.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ti4all.agendaapp.data.Agenda
import com.ti4all.agendaapp.data.AgendaViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EditarContatoScreen(
    viewModel: AgendaViewModel,
    contatoId: Int,
    onVoltar: () -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }

    // Busca os dados atuais
    LaunchedEffect(contatoId) {
        viewModel.buscarPorId(contatoId).collectLatest { contato ->
            nome = contato.nome
            telefone = contato.telefone
        }
    }

    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") }
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = telefone,
            onValueChange = {
                telefone = it
            },
            label = { Text("Telefone") }
        )
        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            viewModel.editar(Agenda(id = contatoId, nome = nome, telefone = telefone))
            onVoltar()
        }) {
            Text("Salvar Alterações")
        }
    }
}
