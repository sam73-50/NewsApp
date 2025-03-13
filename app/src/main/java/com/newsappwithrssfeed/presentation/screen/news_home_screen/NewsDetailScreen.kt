package com.newsappwithrssfeed.presentation.screen.news_home_screen

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.newsappwithrssfedd.R
import com.newsappwithrssfeed.hilt_entry_point.NewApp
import com.newsappwithrssfeed.presentation.common.TopBarContent
import com.newsappwithrssfeed.presentation.navcontroller.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewsDetailScreen(navController: NavController,newsViewModel: NewsViewModel = hiltViewModel()) {
    val showError = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }
    BackHandler {
        navController.navigate(Screen.NewsHomeScreen.route)
    }

    Scaffold(
        topBar = {
            TopBarContent {
                navController.navigate(Screen.NewsHomeScreen.route)
            }
        },
        content = {

            if (newsViewModel.newKistState.value.isLoading){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    CircularProgressIndicator(modifier = Modifier.size(32.dp), color = Color.Gray, strokeWidth = 1.dp)
                }
            }else if(newsViewModel.newKistState.value.error.isNotEmpty()){
                ErrorDialog(showDialog = showError.value, errorMessage = errorMessage.value, onDismiss = { showError.value = false })
            } else{
                NewsDetailScreenContent()
            }
        }
    )
}

@Composable
fun NewsDetailScreenContent() {
   val gApp = (LocalContext.current as Activity).application as NewApp
    val newsItem = gApp.itemData
    if (newsItem == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No News Available", fontSize = 18.sp, color = Color.Gray)
        }
        return
    }
    Box(modifier = Modifier.padding(top = 64.dp)) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            // News Image
            SubcomposeAsyncImage(
                model = newsItem.mediaContent?.url,
                contentDescription = "News Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.LightGray),
                error = {
                    Image(
                        painter = painterResource(id = R.drawable.error),
                        contentDescription = "Error Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                },
                loading = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.Gray, modifier = Modifier.size(32.dp))
                    }
                },
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = newsItem.pubDate ?: "Unknown Date",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = newsItem.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = newsItem.description?: "",
                fontSize = 16.sp,
                color = Color.DarkGray,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(16.dp))

        }
    }

}