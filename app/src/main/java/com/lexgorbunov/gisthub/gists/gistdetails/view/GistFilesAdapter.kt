package com.lexgorbunov.gisthub.gists.gistdetails.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lexgorbunov.gisthub.R
import com.lexgorbunov.gisthub.gists.entity.GistFile
import javax.inject.Inject

class GistFilesAdapter @Inject constructor() : RecyclerView.Adapter<GistFileViewHolder>() {

    private val list: MutableList<GistFile> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GistFileViewHolder {
        return GistFileViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_gist_file, parent, false))
    }

    override fun onBindViewHolder(holder: GistFileViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getGistFileByPos(pos: Int): GistFile? {
        return if (pos == RecyclerView.NO_POSITION || pos >= list.size) {
            null
        } else {
            list[pos]
        }
    }

    fun setList(list: List<GistFile>) {
        val removedSize = this.list.size
        this.list.clear()
        this.list.addAll(list)
        notifyItemRangeRemoved(0, removedSize)
        notifyItemRangeInserted(0, this.list.size)
    }

}
