package com.lexgorbunov.gisthub.gists.gistlist.entity

import com.lexgorbunov.gisthub.gists.entity.Gist
import javax.inject.Inject

interface GistMapper {

    fun map(gist: Gist): GistModel
    fun map(list: List<Gist>): List<GistModel>
}

class GistMapperImpl @Inject constructor() : GistMapper {

    override fun map(gist: Gist): GistModel {
        val title = if (gist.description.isNullOrBlank() || gist.description!!.trim() == "-") {
            var gistTitle = ""
            if (gist.files?.isNotEmpty() == true) {
                gistTitle = gist.files.entries.first().key
                if (gist.files.size > 1) {
                    gistTitle += " (+${gist.files.size - 1} files)"
                }
            }
            gistTitle
        } else {
            gist.description
        }
        return GistModel(
            id = gist.id,
            title = title,
            name = gist.owner?.login ?: "",
            avatarUrl = gist.owner?.avatarUrl,
            updatedAt = gist.updatedAt ?: ""
        )
    }

    override fun map(list: List<Gist>): List<GistModel> = list.map { map(it) }
}