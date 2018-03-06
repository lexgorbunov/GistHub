package com.lexgorbunov.gisthub.app.di

import com.lexgorbunov.gisthub.gists.GistActivity
import com.lexgorbunov.gisthub.gists.GistListActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivitiesModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(GistListActivityModule::class))
    fun mainActivityInjector(): GistActivity

}
