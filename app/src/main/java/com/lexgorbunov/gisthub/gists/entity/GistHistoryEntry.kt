package com.lexgorbunov.gisthub.gists.entity

import com.google.gson.annotations.SerializedName

class GistHistoryEntry {

    @SerializedName("url")
    val url: String? = null //": "https://api.github.com/gists/aa5a315d61ae9438b18d/57a7f021a713b1c5a6a199b54cc514735d2d462f",
    @SerializedName("version")
    val version: String? = null //": "57a7f021a713b1c5a6a199b54cc514735d2d462f"
    @SerializedName("user")
    val user: GitHubUser? = null
    @SerializedName("change_status")
    val changeStatus: GistChangeStatus? = null
    @SerializedName("committed_at")
    val committedAt: String? = null
}
