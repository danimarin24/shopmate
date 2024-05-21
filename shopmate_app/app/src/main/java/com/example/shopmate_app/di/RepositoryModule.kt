package com.example.shopmate_app.di

import com.example.shopmate_app.data.services.networkServices.BoardService
import com.example.shopmate_app.domain.repo.BoardRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideBoardRepository(service: BoardService): BoardRepository {
        return BoardRepository(service)
    }
}
