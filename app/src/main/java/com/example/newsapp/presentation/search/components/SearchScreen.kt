package com.example.newsapp.presentation.search.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapp.presentation.common.SearchBar
import com.example.newsapp.presentation.search.SearchEvent
import com.example.newsapp.presentation.search.SearchState
import com.example.newsapp.utils.Dimen.MediumPadding1
import com.example.newsapp.presentation.common.ArticleList
import com.example.newsapp.presentation.navGraph.ScreenRoute

@Composable
fun SearchScreen(
    modifier : Modifier = Modifier,
    state: SearchState,
    event: (SearchEvent) -> Unit,
    navigate: (String) -> Unit
){
    Column(
        modifier = modifier
            .padding(
                top = MediumPadding1,
                start = MediumPadding1,
                end = MediumPadding1,
            )
            .statusBarsPadding()
            .fillMaxSize()
    ){
        SearchBar(
            text = state.searchQuery,
            readOnly = false,
            onValueChange = { enteredQuery ->
                event(SearchEvent.UpdateSearchQuery(enteredQuery))
            },
            onSearch = {
                event(SearchEvent.SearchNews)
            }
        )

        Spacer(modifier = modifier.height(MediumPadding1))
        state.articles?.let { data ->
            val articles = data.collectAsLazyPagingItems()
            ArticleList(
                articles = articles,
                onClick = {navigate(ScreenRoute.DetailsScreen.route)}
            )
        }
    }
}