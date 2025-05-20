package com.example.mygame
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

enum class Move(val display: String) {
    Rock("Rock"),
    Paper("Paper"),
    Scissors("Scissors")
}

fun getResult(user: Move, computer: Move): String = when {
    user == computer -> "Draw!"
    (user == Move.Rock && computer == Move.Scissors) ||
            (user == Move.Scissors && computer == Move.Paper) ||
            (user == Move.Paper && computer == Move.Rock) -> "You win!"
    else -> "Computer wins!"
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

@Composable
fun GameScreen(onBack: () -> Unit) {
    var userScore by remember { mutableIntStateOf(0) }
    var computerScore by remember { mutableIntStateOf(0) }
    var userMove by remember { mutableStateOf<Move?>(null) }
    var computerMove by remember { mutableStateOf<Move?>(null) }
    var result by remember { mutableStateOf("") }
    var animating by remember { mutableStateOf(false) }
    var animIndex by remember { mutableIntStateOf(0) }
    val moves = Move.values()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = onBack) { Text("Back") }
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = {
                    userScore = 0
                    computerScore = 0
                    result = ""
                    userMove = null
                    computerMove = null
                }) {
                    Text("Reset Score")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Your score: $userScore", style = MaterialTheme.typography.titleMedium)
            Text("Computer score: $computerScore", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MoveImage(userMove, "Your Choice")
                MoveImage(computerMove, "Computer Choice")
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                moves.forEach { move ->
                    Button(
                        onClick = {
                            userMove = move
                            animating = true
                            result = ""
                        },
                        enabled = !animating,
                        modifier = Modifier
                            .width(100.dp)
                            .height(50.dp),
                        shape = RoundedCornerShape(0.dp)
                    ) {
                        Text(
                            text = move.display,
                            fontSize = MaterialTheme.typography.bodySmall.fontSize // Smaller font
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            if (userMove != null) {
                if (animating) {
                    LaunchedEffect(userMove, animating) {
                        repeat(12) {
                            animIndex = (animIndex + 1) % moves.size
                            kotlinx.coroutines.delay(50)
                        }
                        computerMove = moves[Random.nextInt(moves.size)]
                        animating = false
                        val res = getResult(userMove!!, computerMove!!)
                        result = res
                        if (res == "You win!") userScore++
                        if (res == "Computer wins!") computerScore++
                    }
                    Text(
                        "Computer is choosing: ${moves[animIndex].display}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else if (computerMove != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(result, style = MaterialTheme.typography.headlineSmall)
                }
            } else {
                Text("Make your move!", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
