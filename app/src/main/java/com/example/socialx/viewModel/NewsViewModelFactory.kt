package com.example.socialx.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.socialx.api.NewsService

class NewsViewModelFactory(private val service: NewsService): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(
            service
        ) as T
    }
}