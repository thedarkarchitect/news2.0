package com.example.newsapp.presentation.navGraph

sealed class ScreenRoute(val route: String) {
    data object OnBoardingScreen: ScreenRoute("onBoardingScreen")
    data object HomeScreen: ScreenRoute("homeScreen")
    data object SearchScreen: ScreenRoute("searchScreen")
    data object BookmarkScreen: ScreenRoute("bookmarkScreen")
    data object DetailsScreen: ScreenRoute("detailsScreen")
    data object AppStartNavigation: ScreenRoute("appStartNavigation")//this is for the start of the app Screens
    data object NewsNavigation: ScreenRoute("newsNavigation")//this is the whole nav for the news screens
    data object NewsNavigatorScreen: ScreenRoute("newNavigator")
}
