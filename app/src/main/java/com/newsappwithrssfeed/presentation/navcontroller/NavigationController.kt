package com.newsappwithrssfeed.presentation.navcontroller

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.newsappwithrssfeed.presentation.screen.news_home_screen.NewsDetailScreen
import com.newsappwithrssfeed.presentation.screen.news_home_screen.NewsHomeScreen


@Composable
fun setUpNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.NewsHomeScreen.route
    ){

        composable(route = Screen.NewsHomeScreen.route){
            NewsHomeScreen(navController)
        }

        composable(route = Screen.NewsDetailScreen.route){
            NewsDetailScreen(navController)
        }

    }
}
