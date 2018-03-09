package com.lexgorbunov.gisthub.gists.presenter

import android.os.Bundle
import com.lexgorbunov.gisthub.gists.router.GistsRouter
import javax.inject.Inject

interface GistPresenter {

    fun init(savedInstanceState: Bundle?)

}

class GistPresenterImpl @Inject constructor(private val router: GistsRouter): GistPresenter {

    override fun init(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) router.goToGistList()
    }

}
