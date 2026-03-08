package com.renato.tictactoesupreme.ui.theme.components

import android.R.attr.description
import android.R.attr.title
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.renato.tictactoesupreme.model.GameMode

@Composable
fun MenuScreen(onModelSelected: (GameMode) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Tic-tac-Toe Supreme",
            fontSize  = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(48.dp))

        ModeButton(
            title = "Modo Clássico",
            description = "O tradicional jogo 3x3",
            color = Color(0xFF2196F3),
            onClick = { onModelSelected(GameMode.CLASSIC) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        ModeButton(
            title = "Modo Avançado",
            description = "O desafio Supremo (9x9)",
            color = Color(0xFFF44336),
            onClick = { onModelSelected(GameMode.ADVANCED) }
        )
    }
}

@Composable
fun ModeButton(title: String, description: String, color: Color, onClick: () -> Unit){
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(100.dp),
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(description, fontSize = 12.sp, fontWeight = FontWeight.Normal)
        }
    }
}













