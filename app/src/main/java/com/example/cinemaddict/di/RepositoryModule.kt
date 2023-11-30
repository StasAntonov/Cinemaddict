package com.example.cinemaddict.di

import com.example.cinemaddict.domain.repository.HomeRepository
import com.example.cinemaddict.domain.repository.MovieRepository
import com.example.cinemaddict.repository.HomeRepositoryImpl
import com.example.cinemaddict.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun binGenreRepository(
        repository: MovieRepositoryImpl
    ): MovieRepository

    @Binds
    @Singleton
    abstract fun provideHomeRepository(repositoryImpl: HomeRepositoryImpl): HomeRepository
}