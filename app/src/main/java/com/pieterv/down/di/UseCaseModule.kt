package com.pieterv.down.di

import com.pieterv.down.domain.usecase.FetchInfoUseCase
import com.pieterv.down.domain.usecase.FetchInfoUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideUseCase(): FetchInfoUseCase {
        return FetchInfoUseCaseImpl()
    }

}