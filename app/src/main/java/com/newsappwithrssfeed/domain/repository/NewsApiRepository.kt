package com.newsappwithrssfeed.domain.repository

import com.newsappwithrssfeed.common.Resource
import com.newsappwithrssfeed.data.dataclassobj.RSSFeed
import kotlinx.coroutines.flow.Flow

interface NewsApiRepository {
    suspend fun getNewsFeed(): Flow<Resource<RSSFeed>>
}