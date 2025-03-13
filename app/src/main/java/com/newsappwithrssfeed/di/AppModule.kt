package com.newsappwithrssfeed.di

import com.newsappwithrssfeed.common.Constant
import com.newsappwithrssfeed.data.data_source.NewParsingApi
import com.newsappwithrssfeed.data.repository.NewsRepositoryImpl
import com.newsappwithrssfeed.domain.repository.NewsApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val interceptor = HttpLoggingInterceptor()
    private val setIntersepter = interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    val client = OkHttpClient.Builder().followRedirects(true) .addInterceptor(setIntersepter).build()

   @Provides
   @Singleton
   fun provideRetrofit(): Retrofit {
       return Retrofit.Builder()
           .baseUrl(Constant.BASE_URL)
           .client(client)
           .addConverterFactory(SimpleXmlConverterFactory.create())
           .build()
   }

    @Provides
    @Singleton
    fun provideNewsService(retrofit: Retrofit): NewParsingApi {
        return retrofit.create(NewParsingApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewRepository( xmlParsingApi : NewParsingApi) : NewsApiRepository{
        return NewsRepositoryImpl(xmlParsingApi)
    }
}