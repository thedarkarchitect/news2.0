package com.example.newsapp.presentation.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.newsapp.presentation.Dimen.IndicatorSize

@Composable
fun PageIndicator(
    modifier: Modifier = Modifier,
    pageSize: Int,
    selectedPage: Int,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.onPrimaryContainer
){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
    ){
        repeat(pageSize) { page ->
            Box(
                modifier = modifier.size(IndicatorSize).clip(CircleShape)
                    .background(color = if(page == selectedPage) selectedColor else unselectedColor)
            )
        }
    }
}