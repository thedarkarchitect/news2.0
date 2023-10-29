package com.example.newsapp.presentation.navGraph

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.newsapp.presentation.onboarding.components.OnBoardingScreen
import com.example.newsapp.presentation.onboarding.OnBoardingViewModel
import com.example.newsapp.presentation.search.SearchViewModel
import com.example.newsapp.presentation.search.components.SearchScreen


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
                val viewModel = hiltViewModel<SearchViewModel>()
                SearchScreen(
                    state = viewModel.state.value,
                    event = viewModel::onEvent,
                    navigate = {}
                )
            }
        }

    }
}