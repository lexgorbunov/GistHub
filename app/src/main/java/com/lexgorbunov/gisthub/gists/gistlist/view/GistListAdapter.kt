package com.lexgorbunov.gisthub.gists.gistlist.view

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lexgorbunov.gisthub.R
import com.lexgorbunov.gisthub.gists.gistlist.entity.GistModel
import javax.inject.Inject

class GistListAdapter @Inject constructor() : RecyclerView.Adapter<GistListViewHolder>(), OnGistItemClicked {

    private val list: MutableList<GistModel> = mutableListOf()
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

    fun setList(list: List<GistModel>) {
        val diffResult = DiffUtil.calculateDiff(GistListDiffCallback(this.list, list))
        this.list.clear()
        this.list.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    fun addToList(list: List<GistModel>) {
        // Exclude duplicates
        val newList: List<GistModel> = this.list.union(list).toList()
        val diffResult = DiffUtil.calculateDiff(GistListDiffCallback(this.list, newList))
        this.list.clear()
        this.list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onClicked(v: View, pos: Int) {
        onClickedListener?.onGistItemClicked(v, list[pos].id)
    }
}

interface OnGistClicked {
    fun onGistItemClicked(v: View, gistId: String)
}
