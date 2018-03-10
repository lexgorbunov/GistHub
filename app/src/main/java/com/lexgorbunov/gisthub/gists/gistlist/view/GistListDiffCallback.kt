package com.lexgorbunov.gisthub.gists.gistlist.view

import android.support.v7.util.DiffUtil
import android.text.TextUtils
import com.lexgorbunov.gisthub.gists.gistlist.entity.GistModel

class GistListDiffCallback(private val oldItems: List<GistModel>, private val newItems: List<GistModel>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].id == newItems[newItemPosition].id
    }

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return TextUtils.equals(oldItem.updatedAt, newItem.updatedAt)
    }
}
