package com.newsappwithrssfeed.domain.modals



data class NewsItem(
    val title: String,
    val link: String,
    val description: String?,
    val pubDate: String?,
    val creator: String?,
    val mediaContent: MediaContent?
)

data class MediaContent(
    val url: String,
    val height: String,
    val width: String
)
