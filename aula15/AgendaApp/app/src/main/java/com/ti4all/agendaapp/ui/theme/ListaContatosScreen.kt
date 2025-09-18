package com.ti4all.agendaapp.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ti4all.agendaapp.data.Agenda
import com.ti4all.agendaapp.data.AgendaViewModel

@Composable
fun ListaContatosScreen(viewModel: AgendaViewModel, onEditar: (Agenda) -> Unit) {
    val lista by viewModel.agendaList.collectAsState()

    LazyColumn {
        items(lista) { contato ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onEditar(contato) }
                    .padding(8.dp)
            ) {
                Column(Modifier.weight(1f)) {
                    Text(text = contato.nome, style = MaterialTheme.typography.bodyLarge)
                    Text(text = contato.telefone, style = MaterialTheme.typography.bodyMedium)
                }
                IconButton(onClick = { viewModel.deletar(contato.id) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Excluir")
                }
            }
        }
    }
}
