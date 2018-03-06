package com.lexgorbunov.gisthub.app.network

import com.lexgorbunov.gisthub.gists.entity.Gist
import io.reactivex.Single
import retrofit2.http.GET

interface GistService {

    @GET("gists")
    fun loadGists(): Single<List<Gist>>

}
