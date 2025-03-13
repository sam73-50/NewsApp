
package com.newsappwithrssfeed.presentation.screen.news_home_screen

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.newsappwithrssfedd.R
import com.newsappwithrssfeed.domain.modals.NewsItem
import com.newsappwithrssfeed.hilt_entry_point.NewApp
import com.newsappwithrssfeed.presentation.common.TopBarContent
import com.newsappwithrssfeed.presentation.navcontroller.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewsHomeScreen(
    navController: NavController,
    newsViewModel: NewsViewModel = hiltViewModel()
) {

    val showError = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }
    BackHandler {

    }
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.Yellow)) {
        Scaffold(topBar = {
            TopBarContent{

            }
        }, content = {
            if (newsViewModel.newKistState.value.isLoading){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    CircularProgressIndicator(modifier = Modifier.size(32.dp), color = Color.Gray, strokeWidth = 1.dp)
                }
            }else if(newsViewModel.newKistState.value.error.isNotEmpty()){
                ErrorDialog(showDialog = showError.value, errorMessage = errorMessage.value, onDismiss = { showError.value = false })
            } else{
                AllItemListScreenContent(navController,newsViewModel)
            }
        })
    }
}

@Composable
fun AllItemListScreenContent(navController: NavController, newsViewModel: NewsViewModel){
    val newsItemsList = newsViewModel.newKistState.value.response
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 64.dp)
        .padding(20.dp)) {
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            items(newsItemsList) { itemData ->
                ContentView(navController,itemData)
            }
        }
    }
}

@Composable
fun ContentView(navController: NavController, itemData: NewsItem) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                val gApp = (context as Activity).application as NewApp
                gApp.itemData = itemData
                navController.navigate(Screen.NewsDetailScreen.route)
            }
            .padding(8.dp)
            .shadow(4.dp, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                SubcomposeAsyncImage(
                    model = itemData.mediaContent?.url,
                    contentDescription = "newsImage",
                    modifier = Modifier
                        .size(70.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    error = {
                        Image(
                            painter = painterResource(id = R.drawable.error),
                            contentDescription = "Error Image",
                            modifier = Modifier.size(70.dp)
                        )
                    },
                    loading = {
                        Box(
                            modifier = Modifier.size(70.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color.Gray, modifier = Modifier.size(24.dp))
                        }
                    },
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = itemData.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = itemData.pubDate ?: "",
                        fontSize = 12.sp,
                        color = Color.DarkGray
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Image(
                    painter = painterResource(id = R.drawable.nextorange),
                    contentDescription = "Next",
                    modifier = Modifier
                        .size(28.dp)
                )
            }
        }
    }
}


