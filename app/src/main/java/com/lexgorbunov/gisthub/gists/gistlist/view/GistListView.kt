package com.lexgorbunov.gisthub.gists.gistlist.view

import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.lexgorbunov.gisthub.R
import com.lexgorbunov.gisthub.app.utils.buildProgressDialog
import com.lexgorbunov.gisthub.app.utils.toast
import com.lexgorbunov.gisthub.app.utils.tryParseError
import com.lexgorbunov.gisthub.gists.entity.Gist
import javax.inject.Inject

interface GistListView {

    fun init(view: View, onItemClickListener: OnGistClicked, onLoadMore: OnGistLoadMore)
    fun showError(throwable: Throwable)
    fun setList(list: List<Gist>)
    fun toggleProgress(isProgress: Boolean)
    fun addToList(list: List<Gist>)

}

class GistListViewImpl @Inject constructor(private val adapter: GistListAdapter): GistListView {

    private lateinit var view: View
    private lateinit var list: RecyclerView
    private lateinit var emptyView: View
    private lateinit var progressDialog: AlertDialog

    override fun init(view: View, onItemClickListener: OnGistClicked, onLoadMore: OnGistLoadMore) {
        this.view = view
        val context = view.context
        progressDialog = context.buildProgressDialog().create()
        emptyView = view.findViewById(R.id.empty_view)
        emptyView.visibility = if (adapter.itemCount > 0) View.GONE else View.VISIBLE

        list = view.findViewById(R.id.list)
        val linearLayoutManager = LinearLayoutManager(context)
        list.layoutManager = linearLayoutManager
        adapter.onClickedListener = onItemClickListener
        list.addOnScrollListener(EndlessScrollListener(STARTING_PAGE, linearLayoutManager, onLoadMore))
        list.adapter = adapter
        list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    override fun toggleProgress(isProgress: Boolean) {
        if (isProgress) progressDialog.show() else progressDialog.dismiss()
    }

    override fun setList(list: List<Gist>) {
        emptyView.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        adapter.setList(list)
    }

    override fun addToList(list: List<Gist>) {
        if (!list.isEmpty()) {
            emptyView.visibility = View.GONE
        }
        adapter.addToList(list)
    }

    override fun showError(throwable: Throwable) {
        tryParseError(view.context, throwable)?.let {
            view.context.toast(it.localizedMessage)
        }
    }

    companion object {
        private const val STARTING_PAGE = 1
    }

}
