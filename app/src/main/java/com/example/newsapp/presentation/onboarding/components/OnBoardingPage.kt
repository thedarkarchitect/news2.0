package com.example.newsapp.presentation.onboarding.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.utils.Dimen.MediumPadding1
import com.example.newsapp.utils.Dimen.MediumPadding2
import com.example.newsapp.presentation.onboarding.Page
import com.example.newsapp.presentation.onboarding.pages
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
    page: Page
){
    Column(modifier = modifier) {
        Image(
           modifier = modifier
               .fillMaxWidth()
               .fillMaxHeight(fraction = 0.6f)
            ,
            painter = painterResource(id = page.image),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = modifier.height(MediumPadding1))
        Text(
            text = page.title,
            modifier = modifier.padding(horizontal = MediumPadding2),
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
            color = if(isSystemInDarkTheme()) Color.White else Color.Black
        )
        Text(
            text = page.title,
            modifier = modifier.padding(horizontal = MediumPadding2),
            style = MaterialTheme.typography.bodyMedium,
            color = if(isSystemInDarkTheme()) Color.White else Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OnBoardingPagePreview() {
    NewsAppTheme {
        OnBoardingPage(page = pages[0])
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun OnBoardingPage2Preview() {
    NewsAppTheme {
        OnBoardingPage(page = pages[0])
    }
}