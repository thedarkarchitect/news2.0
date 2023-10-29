package com.example.newsapp.presentation.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newsapp.domain.useCases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
): ViewModel() {

    private val _state = mutableStateOf(SearchState())
    val state = _state

    fun onEvent(event: SearchEvent) {
        when(event){
            SearchEvent.SearchNews -> {
                searchNews()
            }
            is SearchEvent.UpdateSearchQuery -> {
                _state.value = state.value.copy(searchQuery = event.searchQuery)
            }
        }
    }

    private fun searchNews() {
        val results = newsUseCases.searchNews(//variable will old the results of the search
            searchQuery = state.value.searchQuery,//query set in the UI is sent to the useCase
            sources = listOf("bbc-news", "abc-news", "al-jazeera-english", "cnn", "bloomberg")
        ).cachedIn(viewModelScope)
        _state.value = state.value.copy(articles =results)
    }
}

