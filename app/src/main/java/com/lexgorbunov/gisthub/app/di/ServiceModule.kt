package com.lexgorbunov.gisthub.app.di

import com.lexgorbunov.gisthub.app.network.GistService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ServiceModule {

    @Singleton
    @Provides
    fun gistService(retrofit: Retrofit): GistService {
        return retrofit.create(GistService::class.java)
    }

}
