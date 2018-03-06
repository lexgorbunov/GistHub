package com.lexgorbunov.gisthub.app.network

import com.lexgorbunov.gisthub.gists.entity.Gist
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GistService {

    @GET("gists/public")
    fun loadGists(): Single<List<Gist>>

    @GET("gists/{gistId}")
    fun loadGist(@Path("gistId") gistId: String): Single<Gist>

}
