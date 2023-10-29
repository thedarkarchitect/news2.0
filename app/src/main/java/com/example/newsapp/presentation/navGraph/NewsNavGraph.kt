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
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapp.presentation.home.HomeViewModel
import com.example.newsapp.presentation.home.components.HomeScreen
import com.example.newsapp.presentation.onboarding.OnBoardingScreen
import com.example.newsapp.presentation.onboarding.OnBoardingViewModel


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
                Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)){
                    OnBoardingScreen(
                        event = viewModel::onEvent
                    )
                }
            }
        }

        navigation(
            route = ScreenRoute.NewsNavigation.route,
            startDestination = ScreenRoute.NewsNavigatorScreen.route
        ){
            composable(
                route = ScreenRoute.NewsNavigatorScreen.route
            ){
                val viewModel = hiltViewModel<HomeViewModel>()
                val articles = viewModel.news.collectAsLazyPagingItems()
                HomeScreen(
                    articles = articles,
                    navigate = {
                        ScreenRoute.NewsNavigation.route
                    }
                )
            }
        }

    }
}