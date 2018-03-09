package com.lexgorbunov.gisthub.app.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.lexgorbunov.gisthub.R
import com.lexgorbunov.gisthub.app.App
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.setLevel(HttpLoggingInterceptor.Level.BODY)

        val builder = OkHttpClient.Builder().addNetworkInterceptor(logger)
        return builder.build()
    }

    @Provides
    fun provideHttpsRetrofit(app: App, gson: Gson, okHttp: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(app.resources.getString(R.string.api_base))
                .client(okHttp)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }
}
