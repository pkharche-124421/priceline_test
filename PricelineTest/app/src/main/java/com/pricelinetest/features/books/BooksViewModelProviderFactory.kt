package com.pricelinetest.features.books

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pricelinetest.data.DataRepository

class BooksViewModelProviderFactory(
    val app: Application,
    private val dataRepository: DataRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BooksViewModel(app, dataRepository) as T
    }
}