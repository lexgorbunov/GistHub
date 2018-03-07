package com.lexgorbunov.gisthub.gists.gistlist.view

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.NO_POSITION
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.lexgorbunov.gisthub.R
import com.lexgorbunov.gisthub.gists.entity.Gist
import com.squareup.picasso.Picasso

class GistListViewHolder(itemView: View, private val clickListener: OnGistItemClicked) : RecyclerView.ViewHolder(itemView) {

    private val title: TextView = itemView.findViewById(R.id.title)
    private val name: TextView = itemView.findViewById(R.id.name)
    private val avatar: ImageView = itemView.findViewById(R.id.avatar)
    private val container: View = itemView.findViewById(R.id.item_container)
    var avatarSize: Int = 0

    init {
        avatarSize = itemView.context.resources.getDimensionPixelSize(R.dimen.gist_avatar_size)
        container.setOnClickListener {
            val pos = adapterPosition
            if (pos == NO_POSITION) return@setOnClickListener
            clickListener.onClicked(it, pos)
        }
    }

    fun bind(gist: Gist) {
        title.text = if (gist.description.isNullOrBlank() || gist.description!!.trim() == "-") {
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
        if (gist.owner != null) {
            name.visibility = View.GONE
            gist.owner.let {
                name.visibility = View.VISIBLE
                name.text = it.login!!
                if (it.avatarUrl.isNullOrBlank()) {
                    avatar.setImageResource(R.drawable.no_user_image)
                } else {
                    Picasso.with(avatar.context).load(it.avatarUrl)
                            .error(R.drawable.no_user_image)
                            .resize(avatarSize, avatarSize)
                            .onlyScaleDown()
                            .into(avatar)
                }
            }
        } else {
            avatar.setImageResource(R.drawable.no_user_image)
            name.visibility = View.GONE
        }
    }

}

interface OnGistItemClicked {
    fun onClicked(v: View, pos: Int)
}

