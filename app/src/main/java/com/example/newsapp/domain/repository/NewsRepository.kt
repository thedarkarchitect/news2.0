package com.example.newsapp.domain.repository

import androidx.paging.PagingData
import com.example.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(sources: List<String>) : Flow<PagingData<Article>>//receive data as a flow in paging data format

    fun searchNews(searchQuery: String ,sources: List<String>) : Flow<PagingData<Article>>
}