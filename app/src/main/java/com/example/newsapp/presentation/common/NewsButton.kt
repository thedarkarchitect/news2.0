package com.example.newsapp.presentation.common

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun NewsButton(
    text: String,
    onClick: () -> Unit
){
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor =  if(isSystemInDarkTheme()) Color.White else Color.Black
        ),
        shape = RoundedCornerShape(size = 6.dp)
    ){
        Text(
           text = text,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
        )
    }
}

@Composable
fun NewsTextButton(
    text: String,
    onClick: () -> Unit
){
    TextButton(
        onClick = onClick,
        shape = RoundedCornerShape(size = 6.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
            color =  if(isSystemInDarkTheme()) Color.White  else Color.Black
        )
    }
}

@Preview
@Composable
fun NewsButtonPreview() {
    NewsAppTheme {
        NewsButton(
            text = "Next",
            onClick = {}
        )
    }
}

@Preview
@Composable
fun NewsButtonPreview2() {
    NewsAppTheme {
        NewsTextButton(
            text = "Back",
            onClick = {}
        )
    }
}