package com.lexgorbunov.gisthub.gists.entity

import com.google.gson.annotations.SerializedName

class Gist {

    @SerializedName("url")
    val url: String? = null //": "https://api.github.com/gists/aa5a315d61ae9438b18d",
    @SerializedName("forks_url")
    val forksUrl: String? = null //": "https://api.github.com/gists/aa5a315d61ae9438b18d/forks",
    @SerializedName("commits_url")
    val commitsUrl: String? = null //": "https://api.github.com/gists/aa5a315d61ae9438b18d/commits",
    @SerializedName("id")
    lateinit var id: String //": "aa5a315d61ae9438b18d",
    @SerializedName("description")
    val description: String? = null //": "description of gist",
    @SerializedName("public")
    val public: Boolean = false //": true
    @SerializedName("owner")
    val owner: GitHubUser? = null
    @SerializedName("user")
    val user: GitHubUser? = null
    @SerializedName("files")
    val files: Map<String, GistFile>? = null
    @SerializedName("truncated")
    val truncated: Boolean = false //": false,
    @SerializedName("comments")
    val comments: Int = 0 //": 0,
    @SerializedName("comments_url")
    val commentsUrl: String? = null //": "https://api.github.com/gists/aa5a315d61ae9438b18d/comments/",
    @SerializedName("html_url")
    val htmlUrl: String? = null //": "https://gist.github.com/aa5a315d61ae9438b18d",
    @SerializedName("git_pull_url")
    val gitPullUrl: String? = null //": "https://gist.github.com/aa5a315d61ae9438b18d.git",
    @SerializedName("git_push_url")
    val gitPushUrl: String? = null //": "https://gist.github.com/aa5a315d61ae9438b18d.git",
    @SerializedName("created_at")
    val createdAt: String? = null //": "2010-04-14T02:15:15Z",
    @SerializedName("updated_at")
    val updatedAt: String? = null //": "2011-06-20T11:34:15Z"

    // For details
    @SerializedName("history")
    val history: List<GistHistoryEntry>? = null
}
