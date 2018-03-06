package com.lexgorbunov.gisthub.gists.gistlist.presenter

import android.support.v4.app.FragmentManager
import android.view.View
import com.lexgorbunov.gisthub.gists.gistlist.view.GistListView
import com.lexgorbunov.gisthub.gists.gistlist.view.OnGistClicked
import com.lexgorbunov.gisthub.gists.repository.GistRepository
import com.lexgorbunov.gisthub.gists.router.GistsRouter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

interface GistListPresenter : OnGistClicked {

    fun init(view: GistListView, fragmentManager: FragmentManager)
    fun destroy()

}

class GistListPresenterImpl @Inject constructor(private val router: GistsRouter,
                                                private val gistRepo: GistRepository) : GistListPresenter {

    private lateinit var fragmentManager: FragmentManager
    private var view: GistListView? = null
    private val subscriptions: CompositeDisposable = CompositeDisposable()
    private var isFirstTimeLoaded = false

    override fun init(view: GistListView, fragmentManager: FragmentManager) {
        this.view = view
        this.fragmentManager = fragmentManager
        if (!isFirstTimeLoaded) loadGistList()
    }

    override fun destroy() {
        subscriptions.clear()
        view = null
    }

    private fun loadGistList() {
        gistRepo.getGists().observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onSuccess = {
                    isFirstTimeLoaded = true
                    view?.setList(it)
                }, onError = {
                    it.printStackTrace()
                    view?.showError(it)
                })
    }

    override fun onGistItemClicked(v: View, gistId: String) {
        router.goToDetails(gistId, fragmentManager)
    }

}
