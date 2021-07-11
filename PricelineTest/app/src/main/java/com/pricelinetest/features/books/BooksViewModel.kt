package com.pricelinetest.features.books

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pricelinetest.R
import com.pricelinetest.data.DataRepository
import com.pricelinetest.network.ResultWrapper
import com.pricelinetest.models.BookDetails
import com.pricelinetest.models.Name
import com.pricelinetest.network.apimodels.response.ResponseBooksList
import kotlinx.coroutines.launch

class BooksViewModel(val app: Application, val dataRepository: DataRepository) : AndroidViewModel(app) {
    var booksList = MutableLiveData<List<BookDetails>>()
    var isLoading = MutableLiveData<Boolean>()
    var isNetworkError = MutableLiveData<Boolean>()
    var errorMsg = MutableLiveData<String>()
    var nameItem: Name? = null

    private fun getBooksList() {
        nameItem?.let {
            showLoader(true)
            viewModelScope.launch {
                Log.d("tag"," ${it.displayName} and ${it.listNameEncoded}")
                //val response = dataRepository.getBooksList(it.listName, it.oldestPublishedDate, it.newestPublishedDate, app.getString(R.string.api_key))

                //https://developer.nytimes.com/docs/books-product/1/routes/lists.json/get Example values, as none other provided any response
                val response = dataRepository.getBooksList("Hardcover Fiction", "2016-03-05", "2016-03-20", app.getString(R.string.api_key))
                when (response) {
                    is ResultWrapper.NetworkError -> showNetworkError()
                    is ResultWrapper.GenericError -> showGenericError(response)
                    is ResultWrapper.Success -> showSuccess(response.value)
                }
                showLoader(false)
            }
        }
    }

    private fun showSuccess(response: ResponseBooksList) {
        if(response.bestSellerList.isNotEmpty()) {
            val list = ArrayList<BookDetails>()
            response.bestSellerList.forEach {
                list.addAll(it.bookDetails)
            }
            booksList.value = list
        } else {
            errorMsg.value = app.getString(R.string.no_books_found)
        }
    }

    private fun showGenericError(genericError: ResultWrapper.GenericError) {
        errorMsg.value = genericError.errorMsg
    }

    private fun showNetworkError() {
        isNetworkError.value = true
    }

    private fun showLoader(b: Boolean) {
        isLoading.value = b
    }

    fun setName(item: Name?) {
        nameItem = item
        getBooksList()
    }
}