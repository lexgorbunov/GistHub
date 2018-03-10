package com.lexgorbunov.gisthub.gists.gistlist.presenter

import android.support.v4.app.FragmentManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.lexgorbunov.gisthub.gists.gistlist.entity.GistMapper
import com.lexgorbunov.gisthub.gists.gistlist.view.GistListView
import com.lexgorbunov.gisthub.gists.gistlist.view.OnGistClicked
import com.lexgorbunov.gisthub.gists.gistlist.view.OnGistLoadMore
import com.lexgorbunov.gisthub.gists.repository.GistRepository
import com.lexgorbunov.gisthub.gists.router.GistsRouter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

interface GistListPresenter : OnGistClicked, OnGistLoadMore {

    fun init(view: GistListView, fragmentManager: FragmentManager)
    fun destroy()
}

class GistListPresenterImpl @Inject constructor(
    private val router: GistsRouter,
    private val gistRepo: GistRepository,
    private val gistMapper: GistMapper
) : GistListPresenter {

    private lateinit var fragmentManager: FragmentManager
    private var view: GistListView? = null
    private val subscriptions: CompositeDisposable = CompositeDisposable()
    private var isFirstTimeLoaded = false

    override fun init(view: GistListView, fragmentManager: FragmentManager) {
        this.view = view
        this.fragmentManager = fragmentManager
        if (view.getGistsCount() > 0) view.hideEmptyView() else view.showEmptyView()
        if (!isFirstTimeLoaded) loadGistList()
    }

    override fun destroy() {
        subscriptions.clear()
        view = null
    }

    private fun loadGistList() {
        view?.let { view ->
            view.showProgress()
            gistRepo.getGists().observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onSuccess = {
                    isFirstTimeLoaded = true
                    view.setList(gistMapper.map(it))
                    if (it.isEmpty()) view.showEmptyView() else view.hideEmptyView()
                    view.hideProgress()
                }, onError = {
                    it.printStackTrace()
                    view.hideProgress()
                    view.showError(it)
                }).let { subscriptions.add(it) }
        }
    }

    override fun onGistItemClicked(v: View, gistId: String) {
        router.goToDetails(gistId)
    }

    override fun onLoadMore(page: Int, totalItemsCount: Int, recycler: RecyclerView) {
        this.view?.let { view ->
            view.showProgress()
            gistRepo.getGists(page).observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onSuccess = {
                    view.addToList(gistMapper.map(it))
                    if (!it.isEmpty()) view.hideEmptyView()
                    view.hideProgress()
                }, onError = {
                    view.hideProgress()
                    view.allowTryRetryLoadMore()
                    view.showError(it)
                    it.printStackTrace()
                }).let { subscriptions.add(it) }
        }
    }
}
