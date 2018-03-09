package com.lexgorbunov.gisthub.gists.gistdetails.view.files

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.lexgorbunov.gisthub.R
import com.lexgorbunov.gisthub.gists.entity.GistFile

class GistFileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val content: TextView = itemView.findViewById(R.id.content)

    fun bind(gistFile: GistFile) {
        content.text = gistFile.content ?: ""
    }

}
