package com.lexgorbunov.gisthub.gists.repository

import com.lexgorbunov.gisthub.app.di.ActivityScope
import com.lexgorbunov.gisthub.app.network.GistService
import com.lexgorbunov.gisthub.gists.entity.Gist
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface GistRepository {

    fun getGists(): Single<List<Gist>>
    fun getGist(id: String): Single<Gist>

}

@ActivityScope
class GistRepositoryImpl @Inject constructor(private val gistService: GistService): GistRepository {

    override fun getGists(): Single<List<Gist>> = gistService.loadGists().subscribeOn(Schedulers.io())

    override fun getGist(id: String): Single<Gist> = gistService.loadGist(id).subscribeOn(Schedulers.io())

}
