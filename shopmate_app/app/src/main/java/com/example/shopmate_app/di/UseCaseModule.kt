package com.example.shopmate_app.di

import com.example.shopmate_app.domain.repo.BoardRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import com.example.shopmate_app.domain.usecases.board.GetBoardsByUserUseCase
import com.example.shopmate_app.domain.usecases.board.GetCardsByBoardUseCase
import com.example.shopmate_app.domain.usecases.board.AddBoardUseCase

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetBoardsByUserUseCase(repository: BoardRepository): GetBoardsByUserUseCase {
        return GetBoardsByUserUseCase(repository)
    }

    @Provides
    fun provideGetCardsByBoardUseCase(repository: BoardRepository): GetCardsByBoardUseCase {
        return GetCardsByBoardUseCase(repository)
    }

    @Provides
    fun provideAddBoardUseCase(repository: BoardRepository): AddBoardUseCase {
        return AddBoardUseCase(repository)
    }
}
