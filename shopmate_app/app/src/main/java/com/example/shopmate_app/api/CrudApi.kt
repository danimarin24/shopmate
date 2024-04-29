package com.example.shopmate_app.api

import android.text.TextUtils
import android.util.Log
import com.bumptech.glide.load.engine.Resource
import com.example.shopmate_app.model.Setting
import com.example.shopmate_app.model.SettingId
import com.example.shopmate_app.model.Stat
import com.example.shopmate_app.model.StatId
import com.example.shopmate_app.model.User
import com.example.shopmate_app.model.UserId
import com.example.shopmate_app.model.Users
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Part
import java.io.File
import java.time.LocalDateTime
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

    fun getUserByEmail(token: String): User? {
        var res : Response<User>? = null

        runBlocking {
            val coroutine = launch {
                res = getRetrofit().create(APIService::class.java).getUserByEmail(token, apiKey)
            }
            coroutine.join()
        }

        return if (res?.isSuccessful!!) {
            res!!.body()
        } else {
            Log.d("getGoogleUser", res?.code().toString())
            null
        }
    }

    fun getUserByGoogleSub(sub: String): User? {
        var res : Response<User>? = null

        runBlocking {
            val coroutine = launch {
                res = getRetrofit().create(APIService::class.java).getUserByGoogleSub(sub, apiKey)
            }
            coroutine.join()
        }

        Log.i("user object", res!!.body().toString())


        return if (res?.isSuccessful!!) {
            res!!.body()
        } else {
            Log.d("getGoogleUser", res?.code().toString())
            null
        }
    }

    fun validateGoogleToken(idTokenString: String): String? {
        var res : Response<String>? = null

        runBlocking {
            val coroutine = launch {
                res = getRetrofit().create(APIService::class.java).validateGoogleToken(idTokenString, apiKey)
            }
            coroutine.join()
        }

        return if (res?.isSuccessful!!) {
            res!!.body()
        } else {
            Log.d("validateGoogleToken", res?.code().toString())
            null
        }
    }

    fun getUsernameGenerated(name: String): String? {
        var res : Response<String>? = null

        runBlocking {
            val coroutine = launch {
                res = getRetrofit().create(APIService::class.java).getUsernameGenerated(name, apiKey)
            }
            coroutine.join()
        }

        return if (res?.isSuccessful!!) {
            res!!.body()
        } else {
            Log.d("getUsernameGenerated", res?.code().toString())
            null
        }
    }


    fun addUser(user: User): Boolean {
        var resposta : Response<UserId>? = null

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


    private fun createPartFromString(stringData: String): RequestBody {
        return stringData.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    fun addUserImage(
        pathFile: String,
        username: String,
        name: String,
        password: String,
        email: String,
        phoneNumber: String,
        registerDateTime: LocalDateTime,
        lastConnectionDateTime: LocalDateTime,
        settingId: UInt,
        statId: UInt
    ): Boolean {
        var newPathFile = TextUtils.substring(
            pathFile,
            TextUtils.indexOf(pathFile, "/storage"),
            pathFile.length
        )
        val file = File(newPathFile)
        if (!file.exists()) {
            Log.i("Error", "La ruta " + newPathFile + " no existeix")
            return false
        } else {
            val photo: RequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())

            // pass all parameters to multipart
            val bodyImage = MultipartBody.Part.createFormData("imatge", file.name, photo)
            val usernameRequest = createPartFromString(username)
            val nameRequest = createPartFromString(name)
            val passwordRequest = createPartFromString(password)
            val emailRequest = createPartFromString(email)
            val phoneNumberRequest = createPartFromString(phoneNumber)
            val registerDateTimeRequest = createPartFromString(registerDateTime.toString())
            val lastConnectionDateTimeRequest = createPartFromString(lastConnectionDateTime.toString())
            val settingIdRequest = createPartFromString(settingId.toString())
            val statIdRequest = createPartFromString(statId.toString())

            var response: Response<Message>? = null
            runBlocking {
                val corrutina = launch {
                    response = getRetrofit().create(APIService::class.java)
                        .addUserPart(
                            bodyImage,
                            usernameRequest,
                            nameRequest,
                            passwordRequest,
                            emailRequest,
                            phoneNumberRequest,
                            registerDateTimeRequest,
                            lastConnectionDateTimeRequest,
                            settingIdRequest,
                            statIdRequest
                            )
                }
                corrutina.join()
            }
            if (response!!.isSuccessful)
                return true
            else {
                Log.i("Error", response!!.code().toString())
                return false
            }
        }
    }


    /*
    // multi insert para el user
    fun addUserCompleted(setting: Setting, stat: Stat, user: User): Boolean {
        var resSetting : Response<SettingId>? = null
        var resStat : Response<StatId>? = null
        var resUser : Response<UserId>? = null

        runBlocking {
            val corrutina = launch {
                resSetting = getRetrofit().create(APIService::class.java).addSetting(setting, apiKey)
            }
            corrutina.join()
        }

        Log.i("test", resSetting?.body()?.settingId!!)

        if (!resSetting?.isSuccessful!!) {
            Log.e("addUserCompleted", "no se ha insertado correctamente el setting")
            return false
        }
        val settingId = resSetting?.body()?.settingId

        runBlocking {
            val corrutina = launch {
                resStat = getRetrofit().create(APIService::class.java).addStat(stat, apiKey)
            }
            corrutina.join()
        }
        if (!resStat?.isSuccessful!!) {
            Log.e("addUserCompleted", "no se ha insertado correctamente el stat")
            runBlocking {
                val corrutina = launch {
                    getRetrofit().create(APIService::class.java).deleteSetting(settingId!!.toInt(), apiKey)
                }
                corrutina.join()
            }
            return false
        }
        val statId = resStat?.body()?.statId

        if (settingId == null || statId == null) {
            Log.d("addUserCompleted", "Los ids son nulos")
            return false
        }

        // Add settingId y statId to user
        user.settingId = settingId.toUInt()
        user.statId = statId.toUInt()

        runBlocking {
            val corrutina = launch {
                resUser = getRetrofit().create(APIService::class.java).addUser(user, apiKey)
            }
            corrutina.join()
        }

        if (resUser?.isSuccessful!!) {
            return true
        } else {
            Log.d("addUserCompleted", "No se ha insertado correctamente el usuario: ${resSetting?.code().toString()}")
            runBlocking {
                val corrutina = launch {
                    getRetrofit().create(APIService::class.java).deleteSetting(settingId.toInt(), apiKey)
                    getRetrofit().create(APIService::class.java).deleteStat(statId.toInt(), apiKey)
                }
                corrutina.join()
            }
            return false
        }
    }
     */



}