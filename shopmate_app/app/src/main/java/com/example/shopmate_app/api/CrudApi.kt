package com.example.shopmate_app.api

import android.util.Log
import com.bumptech.glide.load.engine.Resource
import com.example.shopmate_app.model.User
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

class CrudApi : CoroutineScope {
    private val baseIp = "http://172.16.24.21"
    private val port = "6385"
    private val apiUrl = "${baseIp}:${port}/"
    private val apiKey = "c11cddd5b1554b78b6532b41287bd243"


    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private fun getRetrofit(): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder().baseUrl(apiUrl).client(getClient())
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    private fun getClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }


    fun getUser(username: String): User? {
        var res : Response<User>? = null

        runBlocking {
            val coroutine = launch {
                res = getRetrofit().create(APIService::class.java).getUser(username, apiKey)
            }
            coroutine.join()
        }

        return if (res?.isSuccessful!!) {
            res!!.body()
        } else {
            Log.d("getUser", res?.code().toString())
            null
        }
    }

    fun addUser(user: User): Boolean {
        var resposta : Response<Message>? = null

        runBlocking {
            val corrutina = launch {
                resposta = getRetrofit().create(APIService::class.java).addUser(user, apiKey)
            }
            corrutina.join()
        }

        if (resposta?.isSuccessful!!) {
            return true
        } else {
            Log.d("addUser", resposta?.code().toString())
            return false
        }
    }
}