package com.lexgorbunov.gisthub.app.network

import com.lexgorbunov.gisthub.gists.entity.Gist
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface GistService {

    @GET("gists/public")
    fun loadGists(): Single<List<Gist>>

    @GET("gists/{gistId}")
    fun loadGist(@Path("gistId") gistId: String): Single<Gist>

    @GET
    fun loadFile(@Url fileUrl: String): Single<ResponseBody>

}
