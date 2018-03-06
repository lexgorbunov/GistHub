package com.lexgorbunov.gisthub.app.di

import com.lexgorbunov.gisthub.gists.GistActivity
import com.lexgorbunov.gisthub.gists.GistActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivitiesModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(GistActivityModule::class))
    fun mainActivityInjector(): GistActivity

}
