package com.lexgorbunov.gisthub.gists.gistdetails.view.files

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.lexgorbunov.gisthub.R
import com.lexgorbunov.gisthub.gists.entity.GistFile

class GistFileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val content: TextView = itemView.findViewById(R.id.content)
//    private val name: TextView = itemView.findViewById(R.id.name)
//    private val avatar: ImageView = itemView.findViewById(R.id.avatar)
//    private val container: View = itemView.findViewById(R.id.item_container)

    fun bind(gistFile: GistFile) {
        content.text = gistFile.content ?: ""
    }

}
