package com.lexgorbunov.gisthub.gists

import com.lexgorbunov.gisthub.app.di.FragmentScope
import com.lexgorbunov.gisthub.gists.gistlist.GistListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface GistListActivityModule {

    @FragmentScope
    @ContributesAndroidInjector
    fun gistListFragmentInjector(): GistListFragment

}
