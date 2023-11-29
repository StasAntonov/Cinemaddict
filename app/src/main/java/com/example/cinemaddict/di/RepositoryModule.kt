package com.example.cinemaddict.di

import com.example.cinemaddict.domain.repository.MovieRepository
import com.example.cinemaddict.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun binGenreRepository(
        repository: MovieRepositoryImpl
    ): MovieRepository
}