package com.lexgorbunov.gisthub.gists.presenter

import android.os.Bundle
import android.support.v4.app.FragmentManager
import com.lexgorbunov.gisthub.gists.router.GistsRouter
import javax.inject.Inject

interface GistPresenter {

    fun init(fragmentManager: FragmentManager, savedInstanceState: Bundle?)

}

class GistPresenterImpl @Inject constructor(private val router: GistsRouter): GistPresenter {

    override fun init(fragmentManager: FragmentManager, savedInstanceState: Bundle?) {
        if (savedInstanceState == null) router.goToGistList()
    }

}
