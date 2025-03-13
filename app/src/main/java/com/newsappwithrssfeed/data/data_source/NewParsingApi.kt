package com.newsappwithrssfeed.data.data_source

import com.newsappwithrssfeed.data.dataclassobj.RSSFeed
import retrofit2.Call
import retrofit2.http.GET

interface NewParsingApi {
    @GET("services/xml/rss/nyt/HomePage.xml")
    fun getNewsParsingData(): Call<RSSFeed>
}