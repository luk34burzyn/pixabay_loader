package com.payback.pixabay.di

import android.content.Context
import com.payback.pixabay.data.PixabayImageApiService
import com.payback.pixabay.data.network.ConnectivityInterceptor
import com.payback.pixabay.data.network.ConnectivityInterceptorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideInterceptor(@ApplicationContext context: Context): ConnectivityInterceptor =
        ConnectivityInterceptorImpl(context)

    @Provides
    @Singleton
    fun provideRetrofit(connectivityInterceptor: ConnectivityInterceptor): PixabayImageApiService =
        PixabayImageApiService(
            connectivityInterceptor
        )
}