package com.pricelinetest.data

import com.pricelinetest.network.ApiClient
import com.pricelinetest.network.ResultWrapper
import com.pricelinetest.network.apimodels.response.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class DataRepositoryImpl(): DataRepository {
    override suspend fun getNameList(apikey: String): ResultWrapper<ResponseName> {
        return safeApiCall() { ApiClient.getApiClient().getNames(apikey) }
    }

    override suspend fun getBooksList(name: String, date: String, publishedDate: String, apikey: String): ResultWrapper<ResponseBooksList> {
        return safeApiCall() {
            ApiClient.getApiClient().getBooksList(name, date,publishedDate, apikey)
        }
    }

    private suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultWrapper<T> {
        return withContext(Dispatchers.IO) {
            try {
                ResultWrapper.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> ResultWrapper.NetworkError
                    is HttpException -> {
                        val code = throwable.code()
                        val errorMsg = throwable.message()
                        ResultWrapper.GenericError(code, errorMsg)
                    }
                    else -> {
                        ResultWrapper.GenericError(null, null)
                    }
                }
            }
        }
    }
}