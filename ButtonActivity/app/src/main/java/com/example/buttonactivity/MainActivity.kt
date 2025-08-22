package com.example.buttonactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.buttonactivity.ui.theme.ButtonActivityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ButtonActivityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        //TestButton()
                        //IconButton()
                        //Spacer(modifier = Modifier.height(30.dp))
                        //CustomButton()
                        //ImageBase()
                        //WebImage()
                        CountContainer()
                    }
                }
            }
        }
    }
}

/*@Composable
fun TestButton(modifier: Modifier = Modifier) {
    Button(
        onClick = {
            println("***Botão Padrão Clicado***")
        },
        modifier = modifier
            .fillMaxWidth(0.5f)
            .height(60.dp)
    ) {
        Text(
            text = "Clique aqui",
            fontSize = 20.sp
        )
    }
}*/

/*@Composable
fun IconButton(){
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Button(
            onClick = { println("Botão com icone clicado.") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.8f)
                .height(70.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Icone de coração"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "COM ÍCONE",
                fontSize = 24.sp)
        }
    }
}*/

/*@Composable
fun CustomButton(modifier: Modifier = Modifier) {
    Button(
        onClick = {
            println("Botão Personalizado clicado!")
        },
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(0.8f)
            .height(70.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue,   // cor de fundo
            contentColor = Color.White     // cor do texto
        ),
        shape = RoundedCornerShape(12.dp) // cantos arredondados
    ) {
        Text("Botão Customizado", fontSize = 20.sp)
    }
}*/

/*@Composable
fun ImageBase(){
    Image(
        painter = painterResource(id = R.drawable.avatar01),
        contentDescription = "Descrição da Imagem",
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    )
}*/

/*@Composable
fun WebImage(){
    AsyncImage(
        model = "https://picsum.photos/id/237/300/200.jpg",
        contentDescription = "Imagem da Web",
        placeholder = painterResource(id = R.drawable.avatar01),
        error = painterResource(id = R.drawable.ic_launcher_foreground),
        modifier = Modifier
            //.size(200.dp)
            .fillMaxWidth(1f)
    )
}*/

@Composable
fun Counter(state: Int, onIncrement: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Contagem: $state",
            color = Color.Black,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = onIncrement) {
                Text(text = "Incrementar")
            }
        }
    }
}

@Composable
fun CountContainer(){
    var count by remember { mutableStateOf(0) }
    Counter(
        state = count,
        onIncrement = {count++}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewButtons() {
    ButtonActivityTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            //TestButton()
            Spacer(modifier = Modifier.height(32.dp))
            //CustomButton()
        }
    }
}
