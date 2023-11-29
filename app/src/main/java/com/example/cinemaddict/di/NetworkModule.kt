package com.example.cinemaddict.di

import android.content.Context
import com.example.cinemaddict.BuildConfig
import com.example.cinemaddict.api.MovieApi
import com.example.cinemaddict.network.BaseInterceptor
import com.example.cinemaddict.util.network.LiveNetworkConnection
import com.example.cinemaddict.util.network.NetworkConnection
import com.example.cinemaddict.util.network.NetworkConnectionImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(BaseInterceptor())

        if (BuildConfig.DEBUG) {
            val logging =
                HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
            builder.addInterceptor(logging)
        }

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkConnection(@ApplicationContext context: Context): NetworkConnection =
        NetworkConnectionImpl(context)

    @Provides
    @Singleton
    fun provideLiveNetworkConnection(@ApplicationContext context: Context): LiveNetworkConnection =
        NetworkConnectionImpl(context)
}