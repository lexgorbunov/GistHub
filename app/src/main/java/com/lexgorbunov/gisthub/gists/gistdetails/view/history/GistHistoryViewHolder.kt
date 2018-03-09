package com.lexgorbunov.gisthub.gists.gistdetails.view.history

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.lexgorbunov.gisthub.R
import com.lexgorbunov.gisthub.gists.entity.GistHistoryEntry

class GistHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val version: TextView = itemView.findViewById(R.id.version)
    private val addedCount: TextView = itemView.findViewById(R.id.added_count)
    private val removedCount: TextView = itemView.findViewById(R.id.removed_count)

    fun bind(history: GistHistoryEntry) {
        version.text = history.version ?: ""
        if (history.changeStatus != null) {
            addedCount.text = history.changeStatus.additions.toString()
            removedCount.text = history.changeStatus.deletions.toString()
        } else {
            addedCount.text = "0"
            removedCount.text = "0"
        }
    }
}
