package com.example.socialx.viewModel

import androidx.lifecycle.*
import com.example.socialx.api.NewsService
import com.example.socialx.model.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    private val service: NewsService
): ViewModel() {

    var newsHeadLines: MutableLiveData<Response<News>> = MutableLiveData()

    fun getNewsHeadlines(country: String) = viewModelScope.launch(Dispatchers.IO) {
        val result = service.getTopHeadlines(country)
        newsHeadLines.postValue(result)
    }

    var searchedHeadLines: MutableLiveData<Response<News>> = MutableLiveData()

    fun getSearchedHeadLines(country: String,searchedQuery: String) =
        viewModelScope.launch {
            val result = service.getSearchedTopHeadlines(country, searchedQuery)
            searchedHeadLines.postValue(result)
        }

}

