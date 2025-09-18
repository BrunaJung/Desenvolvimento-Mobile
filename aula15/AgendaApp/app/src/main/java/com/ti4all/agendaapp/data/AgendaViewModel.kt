package com.ti4all.agendaapp.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ti4all.agendaapp.AgendaApplication
import com.ti4all.agendaapp.dao.AgendaDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AgendaViewModel(application: AgendaApplication) : ViewModel() {
    private val agendaDao = application.database.agendaDao()
    private val _agendaList = MutableStateFlow<List<Agenda>>(emptyList())
    val agendaList: StateFlow<List<Agenda>> = _agendaList

    init {
        listarTodos()
    }

    // Construtor padrão necessário para ViewModelProvider
    @Suppress("unused")
    constructor() : this(AgendaApplication.instance)

    fun listarTodos() {
        viewModelScope.launch {
            _agendaList.value = agendaDao.listarTodos()
        }
    }

    fun inserir(agenda: Agenda) {
        viewModelScope.launch {
            agendaDao.inserir(agenda)
            listarTodos()
        }
    }

    fun deletar(id: Int) {
        viewModelScope.launch {
            agendaDao.deletar(id)
            listarTodos()
        }
    }

    fun editar(agenda: Agenda) {
        viewModelScope.launch {
            agendaDao.editar(agenda)
            listarTodos()
        }
    }

    fun buscarPorId(id: Int): Flow<Agenda> {
        return agendaDao.buscarPorId(id)
    }
}