package com.lexgorbunov.gisthub.gists.entity

import com.google.gson.annotations.SerializedName

class GistFile {

    @SerializedName("size")
    val size: Long = 0 //": 932,
    @SerializedName("raw_url")
    val rawUrl: String? = null //": "https://gist.githubusercontent.com/raw/365370/8c4d2d43d178df44f4c03a7f2ac0ff512853564e/ring.erl",
    @SerializedName("type")
    val type: String? = null //": "text/plain",
    @SerializedName("truncated")
    val truncated: Boolean = false //": false,
    @SerializedName("language")
    val language: String? = null //": "Erlang"

}
