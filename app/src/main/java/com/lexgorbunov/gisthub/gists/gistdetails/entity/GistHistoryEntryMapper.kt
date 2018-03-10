package com.lexgorbunov.gisthub.gists.gistdetails.entity

import com.lexgorbunov.gisthub.gists.entity.GistHistoryEntry
import javax.inject.Inject

interface GistHistoryEntryMapper {

    fun map(list: List<GistHistoryEntry>): List<GistHistoryEntryModel>
    fun map(entry: GistHistoryEntry): GistHistoryEntryModel
}

class GistHistoryEntryMapperImpl @Inject constructor() : GistHistoryEntryMapper {

    override fun map(entry: GistHistoryEntry): GistHistoryEntryModel {
        return GistHistoryEntryModel(
            version = entry.version ?: "",
            addedCount = entry.changeStatus?.additions?.toString() ?: "0",
            removedCount = entry.changeStatus?.deletions?.toString() ?: "0"
        )
    }

    override fun map(list: List<GistHistoryEntry>): List<GistHistoryEntryModel> = list.map { map(it) }
}
