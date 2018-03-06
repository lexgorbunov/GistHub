package com.lexgorbunov.gisthub.app

import com.lexgorbunov.gisthub.app.di.DaggerAppComponent
import com.squareup.picasso.LruCache
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

    override fun onCreate() {
        super.onCreate()

        val picasso = Picasso.Builder(this)
                .memoryCache(LruCache(250000))
                .build()
        Picasso.setSingletonInstance(picasso)
    }
}
