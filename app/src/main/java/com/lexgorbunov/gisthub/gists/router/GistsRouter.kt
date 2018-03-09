package com.lexgorbunov.gisthub.gists.router

import android.support.v4.app.FragmentManager
import com.lexgorbunov.gisthub.app.utils.FragmentController
import com.lexgorbunov.gisthub.gists.gistdetails.GistDetailsFragment
import com.lexgorbunov.gisthub.gists.gistlist.GistListFragment
import javax.inject.Inject

interface GistsRouter {

    fun goToDetails(gistId: String)
    fun goToGistList()
}

class GistsRouterImpl @Inject constructor(
    private val fragmentController: FragmentController,
    private val fragmentManager: FragmentManager
) : GistsRouter {

    override fun goToGistList() {
        fragmentController.setFragment(
            fragmentManager = fragmentManager,
            fragment = GistListFragment.getInstance(),
            withBackStack = false,
            clearBackStack = true,
            saveState = false
        )
    }

    override fun goToDetails(gistId: String) {
        fragmentController.setFragment(
            fragmentManager = fragmentManager,
            fragment = GistDetailsFragment.getInstance(gistId),
            withBackStack = true,
            saveState = false
        )
    }
}
