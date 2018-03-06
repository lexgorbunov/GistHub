package com.lexgorbunov.gisthub.gists.entity

import com.google.gson.annotations.SerializedName

class GistChangeStatus {

    @SerializedName("deletions")
    val deletions: Int = 0 // ": 0,
    @SerializedName("additions")
    val additions: Int = 0 // ": 180,
    @SerializedName("total")
    val total: Int = 0 // ": 180

}
