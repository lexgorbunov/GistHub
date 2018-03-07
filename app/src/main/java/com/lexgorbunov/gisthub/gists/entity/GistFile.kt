package com.lexgorbunov.gisthub.gists.entity

import com.google.gson.annotations.SerializedName

class GistFile {

    @SerializedName("filename")
    val fileName: String? = null
    @SerializedName("type")
    val type: String? = null //": "text/plain",
    @SerializedName("language")
    val language: String? = null //": "Erlang"
    @SerializedName("raw_url")
    val rawUrl: String? = null //": "https://gist.githubusercontent.com/raw/365370/8c4d2d43d178df44f4c03a7f2ac0ff512853564e/ring.erl",
    @SerializedName("size")
    val size: Long = 0 //": 932,
    //@SerializedName("truncated")
    //val truncated: Boolean = false //": false,

    // Manual
    var content: String? = null

}
