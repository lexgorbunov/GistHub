package com.lexgorbunov.gisthub.gists.gistlist.view

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.NO_POSITION
import android.view.View
import android.widget.TextView
import com.lexgorbunov.gisthub.R
import com.lexgorbunov.gisthub.gists.entity.Gist

class GistListViewHolder(itemView: View, private val clickListener: OnGistItemClicked) : RecyclerView.ViewHolder(itemView) {

    private val title: TextView = itemView.findViewById(R.id.title)
    private val container: View = itemView.findViewById(R.id.item_container)

    init {
        container.setOnClickListener {
            val pos = adapterPosition
            if (pos == NO_POSITION) return@setOnClickListener
            clickListener.onClicked(it, pos)
        }
    }

    fun bind(gist: Gist) {
        title.text = gist.id
    }

}

interface OnGistItemClicked {
    fun onClicked(v: View, pos: Int)
}

