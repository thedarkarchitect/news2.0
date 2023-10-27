package com.example.newsapp.presentation.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.newsapp.utils.Dimen
import com.example.newsapp.utils.Dimen.PageIndicatorWidth
import com.example.newsapp.presentation.common.NewsButton
import com.example.newsapp.presentation.common.NewsTextButton
import com.example.newsapp.presentation.onboarding.components.OnBoardingPage
import com.example.newsapp.presentation.onboarding.components.PageIndicator
import com.example.newsapp.presentation.onboarding.components.pages
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    event: (OnBoardingEvents) -> Unit
){
    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        val pagerState = rememberPagerState (initialPage = 0) { // this tracks the page you on using the page size
            pages.size
        }

        val buttonState = remember {
            derivedStateOf { //this tracks the page and parses the values needed in the button depending on the screen we on
                when(pagerState.currentPage){ //this changes the buttom values usingin  state
                   0 -> listOf("", "Next")
                   1 -> listOf("Back", "Next")
                   2 -> listOf("Back", "Get Started")
                   else -> listOf("") 
                }
            }
        }
        
        HorizontalPager(state = pagerState) {index -> // this tracks and allows pages to be scrolled horizontally
            OnBoardingPage(page = pages[index])
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimen.MediumPadding2)
                .navigationBarsPadding(),//Adds padding to accommodate the navigation bars insets
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            PageIndicator(
                modifier = Modifier.width(PageIndicatorWidth),
                pageSize = pages.size,
                selectedPage = pagerState.currentPage
            )

            Row(verticalAlignment = Alignment.CenterVertically){

                val scope = rememberCoroutineScope()

                if(buttonState.value[0].isNotEmpty()){
                    NewsTextButton(
                        text = buttonState.value[0],
                        onClick = {
                            scope.launch {//this will track the button values and the animate the scroll of the pages and also track the page state
                                pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
                            }
                        }
                    )
                }

                NewsButton(
                    text = buttonState.value[1],
                    onClick = {
                        scope.launch {
                            if(pagerState.currentPage == 2){
                                event(OnBoardingEvents.SaveAppEntry)
                                //TODO: Navigate to HomeScreen
                            } else {
                                pagerState.animateScrollToPage(
                                    page = pagerState.currentPage + 1
                                )
                            }
                        }
                    }
                )
            }
        }
        Spacer(modifier = Modifier.weight(0.5f))
    }
}