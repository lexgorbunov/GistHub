package com.lexgorbunov.gisthub.gists.entity

import com.google.gson.annotations.SerializedName

class Owner {

    @SerializedName("login")
    val login: String? = null //": "octocat",
    @SerializedName("id")
    val id: Long = 0
    @SerializedName("avatar_url")
    val avatarUrl: String? = null //": "https://github.com/images/error/octocat_happy.gif"
    @SerializedName("gravatar_id")
    val gravatarId: String? = null //": ""
    @SerializedName("url")
    val url: String? = null //": "https://api.github.com/users/octocat"
    @SerializedName("html_url")
    val htmlUrl: String? = null //": "https://github.com/octocat"
    @SerializedName("followers_url")
    val followersUrl: String? = null //": "https://api.github.com/users/octocat/followers"
    @SerializedName("following_url")
    val followingUrl: String? = null //": "https://api.github.com/users/octocat/following{/other_user}"
    @SerializedName("gists_url")
    val gistsUrl: String? = null //": "https://api.github.com/users/octocat/gists{/gist_id}"
    @SerializedName("starred_url")
    val starredUrl: String? = null //": "https://api.github.com/users/octocat/starred{/owner}{/repo}"
    @SerializedName("subscriptions_url")
    val subscriptionsUrl: String? = null //": "https://api.github.com/users/octocat/subscriptions"
    @SerializedName("organizations_url")
    val organizationsUrl: String? = null //": "https://api.github.com/users/octocat/orgs"
    @SerializedName("repos_url")
    val reposUrl: String? = null //": "https://api.github.com/users/octocat/repos"
    @SerializedName("events_url")
    val eventsUrl: String? = null //": "https://api.github.com/users/octocat/events{/privacy}"
    @SerializedName("received_events_url")
    val receivedEvents_url: String? = null //": "https://api.github.com/users/octocat/received_events"
    @SerializedName("type")
    val type: String? = null //": "User"
    @SerializedName("site_admin")
    val siteAdmin: Boolean = false //": false

}
