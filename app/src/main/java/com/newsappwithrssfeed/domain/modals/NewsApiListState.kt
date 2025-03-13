package com.newsappwithrssfeed.domain.modals

data class NewsApiListState(
    val response : List<NewsItem> = emptyList(),
    val isLoading : Boolean = true,
    val error : String = ""
)
