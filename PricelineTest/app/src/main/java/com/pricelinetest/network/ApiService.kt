package com.pricelinetest.network

import com.pricelinetest.network.apimodels.response.ResponseBooksList
import com.pricelinetest.network.apimodels.response.ResponseName
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    companion object {
        val BASE_URL: String = "https://api.nytimes.com/svc/books/v3/"
    }

    //https://api.nytimes.com/svc/books/v3/lists/names.json?api-key=[YOUR_API_KEY]
    @GET("lists/names.json")
    suspend fun getNames(@Query("api-key") apiKey: String): ResponseName

    //"https://api.nytimes.com/svc/books/v3/lists.json?
    // list=Hardcover%20Nonfiction&
    // bestsellers-date=2021-07-18&
    // published-date=2018-07-18&
    // offset=20&api-key=[YOUR_API_KEY]
    @GET("lists.json")
    suspend fun getBooksList(
        @Query("list") name: String,
        @Query("bestsellers-date") date: String,
        @Query("published-date") publishedDate: String,
        @Query("api-key") apiKey: String
    ): ResponseBooksList
}