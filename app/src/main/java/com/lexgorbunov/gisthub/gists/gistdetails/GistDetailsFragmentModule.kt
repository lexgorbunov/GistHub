package com.lexgorbunov.gisthub.gists.gistdetails

import com.lexgorbunov.gisthub.gists.gistdetails.presenter.GistDetailsPresenter
import com.lexgorbunov.gisthub.gists.gistdetails.presenter.GistDetailsPresenterImpl
import com.lexgorbunov.gisthub.gists.gistdetails.view.GistDetailsView
import com.lexgorbunov.gisthub.gists.gistdetails.view.GistDetailsViewImpl
import dagger.Binds
import dagger.Module

@Module
interface GistDetailsFragmentModule {

    @Binds
    fun bindGistDetailsPresenter(presenter: GistDetailsPresenterImpl): GistDetailsPresenter

    @Binds
    fun bindGistDetailsView(view: GistDetailsViewImpl): GistDetailsView
}
