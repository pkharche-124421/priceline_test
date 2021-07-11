package com.pricelinetest.data

import com.pricelinetest.network.ResultWrapper
import com.pricelinetest.network.apimodels.response.ResponseBooksList
import com.pricelinetest.network.apimodels.response.ResponseName


interface DataRepository {
    suspend fun getNameList(apiKey: String): ResultWrapper<ResponseName>

    suspend fun getBooksList(name: String, date: String, publishedDate: String, apikey: String): ResultWrapper<ResponseBooksList>
}