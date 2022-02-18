package me.nyaken.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.nyaken.network.ApiContainer
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class APIModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        retrofit: Retrofit
    ): ApiContainer {
        return retrofit.create(ApiContainer::class.java)
    }

}