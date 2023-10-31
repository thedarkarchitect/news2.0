package com.example.newsapp.presentation.news_navigaor.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapp.R
import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.bookmark.BookMarkViewModel
import com.example.newsapp.presentation.bookmark.components.BookmarkScreen
import com.example.newsapp.presentation.details.DetailsViewModel
import com.example.newsapp.presentation.details.components.DetailsScreen
import com.example.newsapp.presentation.home.HomeViewModel
import com.example.newsapp.presentation.home.components.HomeScreen
import com.example.newsapp.presentation.navGraph.ScreenRoute
import com.example.newsapp.presentation.search.SearchViewModel
import com.example.newsapp.presentation.search.components.SearchScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsNavigator() {
    val bottomNavigationItem = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
            BottomNavigationItem(icon = R.drawable.ic_search, text = "Search"),
            BottomNavigationItem(icon = R.drawable.ic_bookmark, text = "BookMark")
        )
    }
    
    val navController = rememberNavController()
    val backstackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }

    selectedItem = remember(key1 = backstackState) {
        when(backstackState?.destination?.route) {
            ScreenRoute.HomeScreen.route -> 0
            ScreenRoute.SearchScreen.route -> 1
            ScreenRoute.BookmarkScreen.route -> 2
            else -> 0
        }
    }

    val isBottomBarVisible = remember (key1 = backstackState){
        backstackState?.destination?.route == ScreenRoute.HomeScreen.route ||
                backstackState?.destination?.route == ScreenRoute.SearchScreen.route ||
                backstackState?.destination?.route == ScreenRoute.BookmarkScreen.route
    }
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if(isBottomBarVisible) {
                NewsBottomNavigation(
                    items = bottomNavigationItem,
                    selected = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigateToTab(
                                navController,
                                route = ScreenRoute.HomeScreen.route
                            )

                            1 -> navigateToTab(
                                navController,
                                route = ScreenRoute.SearchScreen.route
                            )

                            2 -> navigateToTab(
                                navController,
                                route = ScreenRoute.BookmarkScreen.route
                            )
                        }
                    }
                )
            }
        }
    ) {
        val bottomPadding = it.calculateBottomPadding()

        NavHost(
            navController = navController,
            startDestination = ScreenRoute.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ){
            composable(
                route = ScreenRoute.HomeScreen.route
            ){
                val viewModel = hiltViewModel<HomeViewModel>()
                val articles = viewModel.news.collectAsLazyPagingItems()
                HomeScreen(
                    articles = articles,
                    navigateToSearch = {
                        navigateToTab(navController, ScreenRoute.SearchScreen.route)
                    },
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController
                            ,article = article
                        )
                    },
                    event = viewModel::onEvent,
                    state = viewModel.state.value
                )
            }

            composable(route = ScreenRoute.SearchScreen.route){
                val viewModel = hiltViewModel<SearchViewModel>()
                val state = viewModel.state.value
                OnBackClickStateSaver(navController = navController)
                SearchScreen(
                    state = state,
                    event = viewModel::onEvent,
                    navigateToDetails = {  article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    }
                )
            }

            composable(route = ScreenRoute.DetailsScreen.route){
                val viewModel = hiltViewModel<DetailsViewModel>()
                //TODO: Handle side effect
//                if(viewModel.sideEffect != null){
//                    Toast.makeText(LocalContext.current, viewModel.sideEffect, Toast.LENGTH_SHORT).show()
//                    viewModel.onEvent(DetailsEvent.RemoveSideEffect)
//                }
                navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")
                    ?.let { article ->
                        DetailsScreen(
                            article = article,
                            event = viewModel::onEvent,
                            navigateUp = { navController.navigateUp() },
                            sideEffect = viewModel.sideEffect
                        )
                    }
            }

            composable(route = ScreenRoute.BookmarkScreen.route){
                val viewModel = hiltViewModel<BookMarkViewModel>()
                val state = viewModel.state.value
                BookmarkScreen(
                    state = state,
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    }
                )
            }

        }
    }
}

@Composable
fun OnBackClickStateSaver(navController: NavController) {
    BackHandler(true) {
        navigateToTab(
            navController = navController,
            route = ScreenRoute.HomeScreen.route
        )
    }
}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen){
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}

private fun navigateToDetails(navController: NavController, article: Article) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(
        route = ScreenRoute.DetailsScreen.route
    )
}