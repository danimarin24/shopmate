package com.example.shopmate_app.di

import com.example.shopmate_app.data.services.networkServices.BoardApiClient
import com.example.shopmate_app.data.services.networkServices.BoardService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideBoardService(apiClient: BoardApiClient): BoardService {
        return BoardService(apiClient)
    }
}
