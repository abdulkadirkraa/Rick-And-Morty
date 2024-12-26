package com.abdulkadirkara.rickandmorty.presentation.screens.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ErrorComponent(errorMessage: String) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AlertDialog(
            onDismissRequest = { /* Do nothing */ },
            title = {
                Text(text = "Hata", fontSize = 20.sp, color = Color.Red)
            },
            text = {
                Text(text = errorMessage, fontSize = 16.sp, color = Color.Gray)
            },
            confirmButton = {
                Button(onClick = {
                    // Uygulamayı kapatma işlemi
                    (context as? android.app.Activity)?.finish()
                }) {
                    Text(text = "Uygulamadan Çık")
                }
            },
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(12.dp))
                .padding(8.dp)
        )
    }
}