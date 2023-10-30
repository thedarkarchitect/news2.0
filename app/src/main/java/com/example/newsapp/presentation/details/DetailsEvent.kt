package com.example.newsapp.presentation.details

sealed class DetailsEvent {
    data object SaveArticle : DetailsEvent()
}
