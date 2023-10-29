package com.example.newsapp.presentation.search

sealed class SearchEvent {
    data class UpdateSearchQuery(val searchQuery: String): SearchEvent() // tracks the entered values by the user

    data object SearchNews: SearchEvent()//this is when the user clicks the search
}
