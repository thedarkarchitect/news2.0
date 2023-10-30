package com.example.newsapp.presentation.bookmark

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.useCases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookMarkViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
): ViewModel() {

    private val _state = mutableStateOf(BookmarkState())
    val state = _state

    init {
        viewModelScope.launch {
            getArticles()
        }
    }

    private suspend fun getArticles(){
        newsUseCases.selectArticles().onEach { articleList ->
            _state.value = _state.value.copy( articles = articleList.reversed() )
        }.launchIn(viewModelScope)
    }
}