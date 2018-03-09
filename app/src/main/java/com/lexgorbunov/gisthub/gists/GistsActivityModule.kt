package com.lexgorbunov.gisthub.gists

import android.support.v4.app.FragmentManager
import com.lexgorbunov.gisthub.app.di.FragmentScope
import com.lexgorbunov.gisthub.gists.gistdetails.GistDetailsFragment
import com.lexgorbunov.gisthub.gists.gistdetails.GistDetailsFragmentModule
import com.lexgorbunov.gisthub.gists.gistlist.GistListFragment
import com.lexgorbunov.gisthub.gists.gistlist.GistListFragmentModule
import com.lexgorbunov.gisthub.gists.presenter.GistPresenter
import com.lexgorbunov.gisthub.gists.presenter.GistPresenterImpl
import com.lexgorbunov.gisthub.gists.repository.GistRepository
import com.lexgorbunov.gisthub.gists.repository.GistRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module(includes = [ProvidesGistActivityModule::class])
interface GistActivityModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [GistListFragmentModule::class])
    fun gistListFragmentInjector(): GistListFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [GistDetailsFragmentModule::class])
    fun gistDetailsFragmentInjector(): GistDetailsFragment

    @Binds
    fun bindGistPresenter(presenter: GistPresenterImpl): GistPresenter

    @Binds
    fun bindGistRepository(presenter: GistRepositoryImpl): GistRepository

}

@Module
class ProvidesGistActivityModule {

    @Provides
    fun provideFragmentManager(context: GistActivity): FragmentManager = context.supportFragmentManager

}
