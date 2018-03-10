package com.lexgorbunov.gisthub.gists.gistlist.view

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.NO_POSITION
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.lexgorbunov.gisthub.R
import com.lexgorbunov.gisthub.gists.gistlist.entity.GistModel
import com.squareup.picasso.Picasso

class GistListViewHolder(itemView: View, private val clickListener: OnGistItemClicked) : RecyclerView.ViewHolder(itemView) {

    private val title: TextView = itemView.findViewById(R.id.title)
    private val name: TextView = itemView.findViewById(R.id.name)
    private val avatar: ImageView = itemView.findViewById(R.id.avatar)
    private val container: View = itemView.findViewById(R.id.item_container)
    private var avatarSize: Int = 0

    init {
        avatarSize = itemView.context.resources.getDimensionPixelSize(R.dimen.gist_avatar_size)
        container.setOnClickListener {
            val pos = adapterPosition
            if (pos == NO_POSITION) return@setOnClickListener
            clickListener.onClicked(it, pos)
        }
    }

    fun bind(gist: GistModel) {
        Picasso.with(avatar.context).cancelRequest(avatar)
        title.text = gist.title
        Picasso.with(avatar.context).load(gist.avatarUrl)
            .placeholder(R.drawable.no_user_image)
            .error(R.drawable.no_user_image)
            .resize(avatarSize, avatarSize)
            .onlyScaleDown()
            .into(avatar)

        if (gist.name.isBlank()) {
            name.visibility = View.GONE
        } else {
            name.visibility = View.VISIBLE
            name.text = gist.name
        }
    }
}

interface OnGistItemClicked {
    fun onClicked(v: View, pos: Int)
}
