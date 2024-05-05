package com.example.shopmate_app.core

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private val baseIp = "http://172.16.24.21"
    private val port = "6385"
    private val apiUrl = "${baseIp}:${port}/"
    private val apiKey = "c11cddd5b1554b78b6532b41287bd243"

    fun getRetrofit(): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun getClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    fun getApiKey() = apiKey
}