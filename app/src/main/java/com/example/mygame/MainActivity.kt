package com.example.mygame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mygame.ui.theme.MygameTheme
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.size


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MygameTheme {
                var showGameScreen by remember { mutableStateOf(false) }
                if (showGameScreen) {
                    GameScreen(onBack = { showGameScreen = false })
                } else {
                    StartScreen(onStart = { showGameScreen = true })
                }
            }
        }
    }
}

@Composable
fun StartScreen(onStart: () -> Unit) {
    androidx.compose.material3.Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        androidx.compose.foundation.layout.Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Text(text = "Iskakov Yerasyl 24075716", modifier = Modifier.padding(top = 16.dp))
            androidx.compose.material3.Button(onClick = onStart) {
                Text(text = "Start Game")
            }
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
    MygameTheme {
        Greeting("Android")
    }
    @Composable
    fun MoveImage(move: Move?, label: String) {
        val imageRes = when (move) {
            Move.Rock -> R.drawable.rock
            Move.Paper -> R.drawable.paper
            Move.Scissors -> R.drawable.scissors
            else -> null
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (imageRes != null) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = label,
                    modifier = Modifier.size(150.dp)
                )
            }
            Text(label)
        }
    }
}