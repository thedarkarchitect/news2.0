package com.example.newsapp.presentation.navGraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.newsapp.presentation.news_navigaor.components.NewsNavigator
import com.example.newsapp.presentation.onboarding.OnBoardingViewModel
import com.example.newsapp.presentation.onboarding.components.OnBoardingScreen


@Composable
fun NewsNavGraph(
    startDestination: String,
){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ){

        navigation(//nested navigation
            route = ScreenRoute.AppStartNavigation.route,//this is the collection of the start navigation of the app
            startDestination = ScreenRoute.OnBoardingScreen.route
        ){
            composable(
                route = ScreenRoute.OnBoardingScreen.route
            ){
                val viewModel = hiltViewModel<OnBoardingViewModel>()
                OnBoardingScreen(
                    event = viewModel::onEvent
                )
            }
        }

        navigation(
            route = ScreenRoute.NewsNavigation.route,
            startDestination = ScreenRoute.NewsNavigatorScreen.route
        ){
            composable(
                route = ScreenRoute.NewsNavigatorScreen.route
            ){
                NewsNavigator()
            }
        }

    }
}