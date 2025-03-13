package com.newsappwithrssfeed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.newsappwithrssfeed.presentation.navcontroller.setUpNavGraph
import com.newsappwithrssfeed.ui.theme.NewsAppWithRSSFeedTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NewsAppWithRSSFeedTheme {
                setUpNavGraph(navController)
            }
        }
    }
}

