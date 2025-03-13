package com.newsappwithrssfeed.presentation.navcontroller

import androidx.navigation.NamedNavArgument

sealed class Screen(val route:String, val argument: List<NamedNavArgument> = emptyList()){
    object NewsHomeScreen : Screen("NewsHomeScreen")
    object NewsDetailScreen : Screen("NewsDetailScreenContent")
}