package com.newsappwithrssfeed.data.repository

import com.newsappwithrssfeed.common.Resource
import com.newsappwithrssfeed.data.data_source.NewParsingApi
import com.newsappwithrssfeed.data.dataclassobj.RSSFeed
import com.newsappwithrssfeed.domain.repository.NewsApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val newsService: NewParsingApi) : NewsApiRepository {
    override suspend fun getNewsFeed(): Flow<Resource<RSSFeed>> = flow {
        try {
            emit(Resource.Loading())
            val response = newsService.getNewsParsingData().execute()
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(it))
                } ?: emit(Resource.Error("No data available"))
            } else {
                emit(Resource.Error("Failed to fetch data: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Network error: ${e.message}"))
        }
    }
}