package com.newsappwithrssfeed.domain.usecase

import android.util.Log
import com.newsappwithrssfeed.common.Resource
import com.newsappwithrssfeed.domain.modals.MediaContent
import com.newsappwithrssfeed.domain.modals.NewsItem
import com.newsappwithrssfeed.domain.repository.NewsApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNewsFeedUseCase @Inject constructor(private val newsRepository: NewsApiRepository) {

    suspend fun execute(): Flow<Resource<List<NewsItem>>> {
        return newsRepository.getNewsFeed().map { resource ->
            when (resource) {
                is Resource.Success -> {
                    Log.i("--------------", "execute: ${resource.data}", )
                    val items = resource.data?.channel?.items
                    val newsItems = if (!items.isNullOrEmpty()){
                        items.map {
                            Log.e("mediacontent", "execute: ${it.mediaContent}")
                            Log.e("itemDescription", "execute: ${it.itemDescription}")

                            NewsItem(
                                title = it.title,
                                link = it.itemLink,
                                description = it.itemDescription,
                                pubDate = it.pubDate,
                                creator = it.creator,
                                mediaContent = it.mediaContent?.let { mediaContent ->

                                    MediaContent(
                                        mediaContent.url?:"",
                                        mediaContent.height?:"",
                                        mediaContent.width?:""
                                    )
                                }
                            )
                        }
                    } else {
                        emptyList()
                    }
                    Resource.Success(newsItems)
                }
                is Resource.Loading -> Resource.Loading()
                is Resource.Error -> Resource.Error(resource.message ?: "Unknown error")
            }
        }
    }
}
