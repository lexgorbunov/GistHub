package com.lexgorbunov.gisthub.gists.gistlist.entity

data class GistModel(
    val id: String,
    val title: String,
    val name: String,
    val avatarUrl: String?,
    val updatedAt: String
)
