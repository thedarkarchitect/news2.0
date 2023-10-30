package com.example.newsapp.domain.useCases.news

import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class SelectArticles(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(): Flow<List<Article>>{
        return newsRepository.selectArticles()
    }
}