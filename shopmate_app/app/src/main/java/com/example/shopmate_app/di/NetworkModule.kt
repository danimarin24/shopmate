package com.example.shopmate_app.di

import com.example.shopmate_app.data.constants.AppConstants
import com.example.shopmate_app.data.services.networkServices.BoardApiClient
import com.example.shopmate_app.data.services.networkServices.CardApiClient
import com.example.shopmate_app.data.services.networkServices.CategoryApiClient
import com.example.shopmate_app.data.services.networkServices.ColorApiClient
import com.example.shopmate_app.data.services.networkServices.UnitApiClient
import com.example.shopmate_app.data.services.networkServices.UserApiClient
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_API_URL)
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

    @Singleton
    @Provides
    fun providesUserApiClient(retrofit: Retrofit):UserApiClient{
        return retrofit.create(UserApiClient::class.java)
    }
    @Singleton
    @Provides
    fun providesColorApiClient(retrofit: Retrofit):ColorApiClient{
        return retrofit.create(ColorApiClient::class.java)
    }

    @Singleton
    @Provides
    fun providesBoardApiClient(retrofit: Retrofit):BoardApiClient{
        return retrofit.create(BoardApiClient::class.java)
    }

    @Singleton
    @Provides
    fun providesCardApiClient(retrofit: Retrofit):CardApiClient{
        return retrofit.create(CardApiClient::class.java)
    }

    @Singleton
    @Provides
    fun providesCategoryApiClient(retrofit: Retrofit):CategoryApiClient{
        return retrofit.create(CategoryApiClient::class.java)
    }

    @Singleton
    @Provides
    fun providesUnitApiClient(retrofit: Retrofit):UnitApiClient{
        return retrofit.create(UnitApiClient::class.java)
    }
}