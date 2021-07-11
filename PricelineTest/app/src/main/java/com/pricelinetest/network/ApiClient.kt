package com.pricelinetest.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.google.gson.internal.bind.DateTypeAdapter
import com.pricelinetest.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object {
        private var apiService: ApiService? = null

        fun getApiClient(): ApiService {
            if (apiService == null) {
                synchronized(ApiClient::class.java) {
                    apiService = getRetrofit().create(
                        ApiService::class.java
                    )
                }
            }
            return apiService!!
        }

        private fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(getGsonConverterFactory())
                .build()
        }

        private fun getGsonConverterFactory(): GsonConverterFactory {
            val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .registerTypeAdapter(Date::class.java, DateTypeAdapter())
                .create()
            return GsonConverterFactory.create(gson)
        }

        private fun getOkHttpClient(): OkHttpClient {
            val builder = OkHttpClient.Builder()
            builder.connectTimeout(10, TimeUnit.SECONDS)
            builder.readTimeout(15, TimeUnit.SECONDS)
            builder.addInterceptor(getHttpLoggingInterceptor())
            return builder.build()
        }

        private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor()
                .setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
        }
    }
}