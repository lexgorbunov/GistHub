package com.lexgorbunov.gisthub.gists.router

import android.support.v4.app.FragmentManager
import com.lexgorbunov.gisthub.app.utils.FragmentController
import com.lexgorbunov.gisthub.gists.gistdetails.GistDetailsFragment
import com.lexgorbunov.gisthub.gists.gistlist.GistListFragment
import javax.inject.Inject

interface GistsRouter {

    fun goToDetails(gistId: String, fragmentManager: FragmentManager)

    fun goToGistList(fragmentManager: FragmentManager)

}

class GistsRouterImpl @Inject constructor(): GistsRouter {

    @Inject
    lateinit var fragmentController: FragmentController

    override fun goToGistList(fragmentManager: FragmentManager) {
        fragmentController.setFragment(
                fragmentManager = fragmentManager,
                fragment = GistListFragment.getInstance(),
                withBackStack = false,
                clearBackStack = true,
                saveState = false
        )
    }

    override fun goToDetails(gistId: String, fragmentManager: FragmentManager) {
        fragmentController.setFragment(
                fragmentManager = fragmentManager,
                fragment = GistDetailsFragment.getInstance(gistId),
                withBackStack = true,
//                reuse = true,
                saveState = false
        )
    }

}
