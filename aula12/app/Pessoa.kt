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

data class Pessoa(
    val nome : String,
    val numeroTelefone : String,
    val descricao : String)
