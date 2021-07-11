package com.pricelinetest.features.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pricelinetest.DateComparator
import com.pricelinetest.R
import com.pricelinetest.data.DataRepository
import com.pricelinetest.network.ResultWrapper
import com.pricelinetest.models.Name
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

class HomeViewModel(val app: Application, private val dataRepository: DataRepository) : AndroidViewModel(app) {
    var nameList = MutableLiveData<List<Name>>()
    var isLoading = MutableLiveData<Boolean>()
    var isNetworkError = MutableLiveData<Boolean>()
    var errorMsg = MutableLiveData<String>()

    init {
        getNameList()
    }

    fun getNameList() {
        showLoader(true)
        viewModelScope.launch {
            val response = dataRepository.getNameList(app.getString(R.string.api_key))
            when (response) {
                is ResultWrapper.NetworkError -> showNetworkError()
                is ResultWrapper.GenericError -> showGenericError(response)
                is ResultWrapper.Success -> showSuccess(response.value.nameList)
            }
            showLoader(false)
        }
    }

    private fun showSuccess(list: List<Name>) {
        val resultList = ArrayList<Name>()

        val weeklyList = ArrayList<Name>()
        val monthlyList = ArrayList<Name>()
        list.forEach {
            if(it.updated.contentEquals("weekly", ignoreCase = true)) {
                weeklyList.add(it)
            } else if(it.updated.contentEquals("monthly", ignoreCase = true)) {
                monthlyList.add(it)
            }
        }
        weeklyList.sortWith(DateComparator)
        monthlyList.sortWith(DateComparator)

        val header1 = Name(displayName = "Weekly (${weeklyList.size})", "", "", "", "", "")
        header1.isHeader = true
        resultList.add(header1)
        resultList.addAll(weeklyList)

        val header2 = Name(displayName = "Monthly (${monthlyList.size})", "", "", "", "", "")
        header2.isHeader = true
        resultList.add(header2)
        resultList.addAll(monthlyList)

        nameList.postValue(resultList)
    }

    private fun showGenericError(genericError: ResultWrapper.GenericError) {
        errorMsg.postValue(genericError.errorMsg)
    }

    private fun showNetworkError() {
        isNetworkError.postValue(true)
    }

    private fun showLoader(b: Boolean) {
        isLoading.postValue(b)
    }
}