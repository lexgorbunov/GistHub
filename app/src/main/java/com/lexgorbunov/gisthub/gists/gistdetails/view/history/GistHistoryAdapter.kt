package com.lexgorbunov.gisthub.gists.gistdetails.view.history

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lexgorbunov.gisthub.R
import com.lexgorbunov.gisthub.gists.gistdetails.entity.GistHistoryEntryModel
import javax.inject.Inject

class GistHistoryAdapter @Inject constructor() : RecyclerView.Adapter<GistHistoryViewHolder>() {

    private val list: MutableList<GistHistoryEntryModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GistHistoryViewHolder {
        return GistHistoryViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_gist_history, parent, false))
    }

    override fun onBindViewHolder(holder: GistHistoryViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: List<GistHistoryEntryModel>) {
        val removedSize = this.list.size
        this.list.clear()
        this.list.addAll(list)
        notifyItemRangeRemoved(0, removedSize)
        notifyItemRangeInserted(0, this.list.size)
    }
}
