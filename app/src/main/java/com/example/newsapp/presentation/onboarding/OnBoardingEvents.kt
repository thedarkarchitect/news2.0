package com.example.newsapp.presentation.onboarding

sealed class OnBoardingEvents {
    data object SaveAppEntry: OnBoardingEvents()// this is trigger on the getSTarted buttom click
}