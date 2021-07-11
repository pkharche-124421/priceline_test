package com.pricelinetest.features.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pricelinetest.data.DataRepository

class HomeViewModelProviderFactory(
    val app: Application,
    private val dataRepository: DataRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(app, dataRepository) as T
    }
}