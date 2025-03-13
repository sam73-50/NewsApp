package com.newsappwithrssfeed.presentation.screen.news_home_screen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsappwithrssfeed.common.Resource
import com.newsappwithrssfeed.domain.modals.NewsApiListState
import com.newsappwithrssfeed.domain.usecase.GetNewsFeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getNewsFeedUseCase: GetNewsFeedUseCase,
) : ViewModel() {

    private val _newKistState = mutableStateOf(NewsApiListState())
   val newKistState  : MutableState<NewsApiListState> = _newKistState

    init {
        fetchNews()
    }

    private fun fetchNews() {
        _newKistState.value = newKistState.value.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            getNewsFeedUseCase.execute().collect { resource ->
                withContext(Dispatchers.Main) {
                    when (resource) {
                        is Resource.Loading -> {
                            _newKistState.value = newKistState.value.copy(isLoading = true)
                        }
                        is Resource.Success -> {
                            _newKistState.value = newKistState.value.copy(isLoading = false, response = resource.data?: emptyList())
                        }
                        is Resource.Error -> {
                            _newKistState.value = newKistState.value.copy(isLoading = false, error = resource.message?:"Unknown Error")
                            Log.e("NewsViewModel", "Error: ${resource.message}")
                        }
                    }
                }
            }
        }
    }

}

