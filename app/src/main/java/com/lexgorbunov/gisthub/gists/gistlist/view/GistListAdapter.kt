package com.lexgorbunov.gisthub.gists.gistlist.view

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lexgorbunov.gisthub.R
import com.lexgorbunov.gisthub.gists.entity.Gist
import javax.inject.Inject

class GistListAdapter @Inject constructor() : RecyclerView.Adapter<GistListViewHolder>(), OnGistItemClicked {

    private val list: MutableList<Gist> = mutableListOf()
    var onClickedListener: OnGistClicked? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GistListViewHolder {
        val holder = GistListViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_gist, parent, false), this)
        return holder
    }

    override fun onBindViewHolder(holder: GistListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: List<Gist>) {
        val diffResult = DiffUtil.calculateDiff(GistListDiffCallback(this.list, list))
        this.list.clear()
        this.list.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onClicked(v: View, pos: Int) {
        onClickedListener?.onGistItemClicked(v, list[pos].id)
    }

}

interface OnGistClicked {
    fun onGistItemClicked(v: View, gistId: String)
}
