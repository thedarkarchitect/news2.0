package com.example.newsapp.presentation.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import com.example.newsapp.R
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException

//CONTROLS WHAT HAPPENS IF THERE IS AN EMPTY SCREEN

@Composable
fun EmptyScreen(error: LoadState.Error? = null){
    var message by remember {
        mutableStateOf(parseErrorMessage(error = error))
    }
    var icon by remember {
        mutableIntStateOf(R.drawable.ic_network_error)
    }

    if(error == null){
        message = "You have not saved news so far!"
        icon = R.drawable.ic_search_document
    }

    var startAnimation by remember {
        mutableStateOf(true)
    }

    val alphaAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 0.3f else 0f,
        animationSpec = tween(1000),
        label = ""
    )

    LaunchedEffect(key1 = true){
        startAnimation = true
    }

    EmptyContent(
        alphaAmin = alphaAnimation,
        message = message,
        iconResource = icon
    )
}

@Composable
fun EmptyContent(
    alphaAmin: Float,
    message: String,
    iconResource: Int
) {
    Icon(
       painter = painterResource(id = iconResource),
        contentDescription = null,
        tint = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray
    )
    Text(
        modifier = Modifier
            .padding(10.dp)
            .alpha(alphaAmin),
        text = message,
        style = MaterialTheme.typography.bodyMedium,
        color = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray
    )
}


fun parseErrorMessage(error: LoadState.Error?): String{
    return when(error?.error) {
        is SocketTimeoutException -> {
            "Server Unavailable."
        }

        is ConnectException -> {
            "Internet Unavailable."
        }

        is IOException -> {
            "Flow Error."
        }
        else -> {
            "Unknown Error."
        }
    }
}
