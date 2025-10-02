package com.example.calculator

import com.example.calculator.ui.theme.CalculatorTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    SimpleCalculator()
                }
            }
        }
    }
}

@Composable
fun SimpleCalculator() {
    var number1 by rememberSaveable { mutableStateOf("") }
    var number2 by rememberSaveable { mutableStateOf("") }
    var result by rememberSaveable { mutableStateOf<String?>(null) }

    fun calculate(op: String) {
        val num1 = number1.toDoubleOrNull()
        val num2 = number2.toDoubleOrNull()

        if (num1 == null || num2 == null) {
            result = "Digite números válidos!"
            return
        }

        result = when (op) {
            "+" -> (num1 + num2).toString()
            "-" -> (num1 - num2).toString()
            "*" -> (num1 * num2).toString()
            "/" -> if (num2 != 0.0) (num1 / num2).toString() else "Erro: Divisão por zero"
            else -> ""
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = number1,
            onValueChange = { number1 = it },
            label = { Text("Número 1") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = number2,
            onValueChange = { number2 = it },
            label = { Text("Número 2") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf("+", "-", "*", "/").forEach { op ->
                Button(
                    onClick = { calculate(op) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = op, fontSize = 24.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Resultado: ${result ?: ""}",
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
