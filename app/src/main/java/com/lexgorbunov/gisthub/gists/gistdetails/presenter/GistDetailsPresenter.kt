package com.lexgorbunov.gisthub.gists.gistdetails.presenter

import com.lexgorbunov.gisthub.gists.gistdetails.entity.GistHistoryEntryMapper
import com.lexgorbunov.gisthub.gists.gistdetails.view.GistDetailsView
import com.lexgorbunov.gisthub.gists.repository.GistRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

interface GistDetailsPresenter {

    fun init(gistId: String, view: GistDetailsView)
    fun destroy()
}

class GistDetailsPresenterImpl @Inject constructor(
    private val gistRepo: GistRepository,
    private val gistHistoryMapper: GistHistoryEntryMapper
) : GistDetailsPresenter {

    private var view: GistDetailsView? = null
    private val subscriptions: CompositeDisposable = CompositeDisposable()
    lateinit var gistId: String

    override fun init(gistId: String, view: GistDetailsView) {
        this.gistId = gistId
        this.view = view
        loadGistDetails()
    }

    override fun destroy() {
        subscriptions.clear()
        view = null
    }

    private fun loadGistDetails() {
        view?.let { view ->
            view.showProgress()
            gistRepo.getGist(gistId).observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onSuccess = { gist ->
                    view.displayDetails(
                        descr = gist.description ?: "",
                        filesList = gist.files?.values?.toList() ?: listOf(),
                        historyList = gistHistoryMapper.map(gist.history ?: listOf())
                    )
                    gist.owner?.let { owner ->
                        if (owner.avatarUrl.isNullOrBlank()) view.hideAvatar() else view.showAvatar(owner.avatarUrl!!)
                        if (owner.login.isNullOrBlank()) view.hideName() else view.showName(owner.login!!)
                    }
                    view.hideProgress()
                }, onError = {
                    it.printStackTrace()
                    view.hideProgress()
                    view.showError(it)
                }).let { subscriptions.add(it) }
        }
    }
}
