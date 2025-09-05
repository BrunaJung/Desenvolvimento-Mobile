package com.example.myapplication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val _pessoa = mutableStateOf<Pessoa?>(null)
    val pessoa: Pessoa?
        get() = _pessoa.value

    fun setPessoa(pessoa: Pessoa) {
        _pessoa.value = pessoa
    }
}