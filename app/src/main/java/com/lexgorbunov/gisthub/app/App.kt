package com.lexgorbunov.gisthub.app

import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.beta.Beta
import com.crashlytics.android.core.CrashlyticsCore
import com.evernote.android.job.JobManager
import com.facebook.stetho.Stetho
import com.lexgorbunov.publicobserver.BuildConfig
import com.lexgorbunov.publicobserver.app.di.DaggerAppComponent
import com.lexgorbunov.publicobserver.job.POJobCreator
import com.lexgorbunov.publicobserver.utils.NotificationUtils
import com.squareup.picasso.LruCache
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.fabric.sdk.android.Fabric

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
