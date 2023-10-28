package com.example.newsapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.domain.model.Article
import java.lang.Exception

class NewsPagingSource(
    private val newsApi: NewsApi,
    private val sources: String
): PagingSource<Int, Article>() {

    private var totalNewsCount = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1 //this gets the page number but if it's null it sets it to 1
        return try {
            val newsResponse = newsApi.getNews(sources = sources, page = page)
            totalNewsCount += newsResponse.articles.size
            val articles = newsResponse.articles.distinctBy { it.title } // removes duplicates by title
//            result of a load result
            LoadResult.Page(//this is the load result object in form of a Page
                data = articles,//the data is the list of articles from the response
                nextKey = if(totalNewsCount == newsResponse.totalResults) null else page + 1,//if the next key is the last article return null or get the next page
                prevKey = null//at the beginning the previous key is null
            )
        }catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(
                throwable = e
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {  anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}