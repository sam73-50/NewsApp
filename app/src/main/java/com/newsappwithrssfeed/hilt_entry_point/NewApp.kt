package com.newsappwithrssfeed.hilt_entry_point

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import androidx.work.Configuration
import com.newsappwithrssfeed.domain.modals.NewsItem
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class NewApp  :  MultiDexApplication(), Configuration.Provider {

    @Inject
    lateinit var workeFactoryManeger : HiltWorkerFactory

    var itemData : NewsItem? = null

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workeFactoryManeger)
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()
    }


}