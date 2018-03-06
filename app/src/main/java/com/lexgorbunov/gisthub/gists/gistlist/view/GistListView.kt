package com.lexgorbunov.gisthub.gists.gistlist.view

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.lexgorbunov.gisthub.R
import com.lexgorbunov.gisthub.app.utils.toast
import com.lexgorbunov.gisthub.app.utils.tryParseError
import com.lexgorbunov.gisthub.gists.entity.Gist
import javax.inject.Inject

interface GistListView {

    fun init(view: View, onItemClickListener: OnGistClicked)

    //fun showToast(text: String)
    fun showError(throwable: Throwable)

    fun setList(list: List<Gist>)
}

class GistListViewImpl @Inject constructor(private val adapter: GistListAdapter): GistListView {

    private lateinit var view: View
    private lateinit var list: RecyclerView
    private lateinit var emptyView: View

    override fun init(view: View, onItemClickListener: OnGistClicked) {
        this.view = view
        val context = view.context

        emptyView = view.findViewById(R.id.empty_view)
        emptyView.visibility = if (adapter.itemCount > 0) View.GONE else View.VISIBLE

        list = view.findViewById(R.id.list)
        list.layoutManager = LinearLayoutManager(context)
        adapter.onClickedListener = onItemClickListener
        list.adapter = adapter
        list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    //override fun showToast(text: String) {
    //    view.context.toast(text)
    //}

    override fun setList(list: List<Gist>) {
        emptyView.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        adapter.setList(list)
    }

    override fun showError(throwable: Throwable) {
        tryParseError(view.context, throwable)?.let {
            view.context.toast(it.localizedMessage)
        }
    }

}
