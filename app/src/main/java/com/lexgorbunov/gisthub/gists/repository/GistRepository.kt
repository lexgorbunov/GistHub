package com.lexgorbunov.gisthub.gists.repository

import com.lexgorbunov.gisthub.app.di.ActivityScope
import com.lexgorbunov.gisthub.app.network.GistService
import com.lexgorbunov.gisthub.gists.entity.Gist
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface GistRepository {

    fun getGists(page: Int = 1): Single<List<Gist>>
    fun getGist(id: String): Single<Gist>

}

@ActivityScope
class GistRepositoryImpl @Inject constructor(private val gistService: GistService) : GistRepository {

    override fun getGists(page: Int): Single<List<Gist>> = gistService.loadGists(page).subscribeOn(Schedulers.io())

    override fun getGist(id: String): Single<Gist> = gistService.loadGist(id).subscribeOn(Schedulers.io()).flatMap { gist ->
        val files = (gist.files ?: mapOf()).values.toList()

        Observable.fromIterable(files).filter { !it.rawUrl.isNullOrBlank() }
                .flatMapSingle { gistFile ->
                    gistService.loadFile(gistFile.rawUrl!!).map {
                        gistFile.content = it.string()
                        gistFile
                    }
                }.toList().map {
                    gist
                }
    }

}
