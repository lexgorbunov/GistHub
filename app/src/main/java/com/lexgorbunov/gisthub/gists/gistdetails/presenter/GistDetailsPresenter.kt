package com.lexgorbunov.gisthub.gists.gistdetails.presenter

import android.support.v4.app.FragmentManager
import com.lexgorbunov.gisthub.gists.gistdetails.view.GistDetailsView
import com.lexgorbunov.gisthub.gists.repository.GistRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

interface GistDetailsPresenter {

    fun init(gistId: String, view: GistDetailsView, fragmentManager: FragmentManager)
    fun destroy()

}

class GistDetailsPresenterImpl @Inject constructor(private val gistRepo: GistRepository) : GistDetailsPresenter {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var view: GistDetailsView
    private val subscriptions: CompositeDisposable = CompositeDisposable()
    lateinit var gistId: String

    override fun init(gistId: String, view: GistDetailsView, fragmentManager: FragmentManager) {
        this.gistId = gistId
        this.view = view
        this.fragmentManager = fragmentManager
        loadGistDetails()
    }

    override fun destroy() {
        subscriptions.clear()
    }

    private fun loadGistDetails() {
        gistRepo.getGist(gistId).observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onSuccess = {
                    view.displayDetails(it)
                }, onError = {
                    it.printStackTrace()
                    view.showError(it)
                })
    }

}
