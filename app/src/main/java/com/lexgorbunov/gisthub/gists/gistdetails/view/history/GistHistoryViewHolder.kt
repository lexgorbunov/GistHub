package com.lexgorbunov.gisthub.gists.gistdetails.view.history

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.lexgorbunov.gisthub.R
import com.lexgorbunov.gisthub.gists.gistdetails.entity.GistHistoryEntryModel

class GistHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val version: TextView = itemView.findViewById(R.id.version)
    private val addedCount: TextView = itemView.findViewById(R.id.added_count)
    private val removedCount: TextView = itemView.findViewById(R.id.removed_count)

    fun bind(history: GistHistoryEntryModel) {
        version.text = history.version
        addedCount.text = history.addedCount
        removedCount.text = history.removedCount
    }
}
