package com.lexgorbunov.gisthub.gists.gistlist

import com.lexgorbunov.gisthub.gists.gistlist.presenter.GistListPresenter
import com.lexgorbunov.gisthub.gists.gistlist.presenter.GistListPresenterImpl
import com.lexgorbunov.gisthub.gists.gistlist.view.GistListView
import com.lexgorbunov.gisthub.gists.gistlist.view.GistListViewImpl
import dagger.Binds
import dagger.Module

@Module
interface GistListFragmentModule {

    @Binds
    fun bindGistListView(view: GistListViewImpl): GistListView

    @Binds
    fun bindGistListPresenter(presenter: GistListPresenterImpl): GistListPresenter

}
