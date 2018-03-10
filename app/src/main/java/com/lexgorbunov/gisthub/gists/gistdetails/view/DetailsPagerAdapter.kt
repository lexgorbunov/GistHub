package com.lexgorbunov.gisthub.gists.gistdetails.view

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lexgorbunov.gisthub.R
import com.lexgorbunov.gisthub.gists.entity.GistFile
import com.lexgorbunov.gisthub.gists.gistdetails.entity.GistHistoryEntryModel
import com.lexgorbunov.gisthub.gists.gistdetails.view.files.GistFilesAdapter
import com.lexgorbunov.gisthub.gists.gistdetails.view.history.GistHistoryAdapter
import kotlinx.android.synthetic.main.page_commits.view.*
import kotlinx.android.synthetic.main.page_files.view.*

class DetailsPagerAdapter constructor(private val context: Context) : PagerAdapter() {

    private val filesAdapter: GistFilesAdapter = GistFilesAdapter()
    private val historyAdapter: GistHistoryAdapter = GistHistoryAdapter()

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun isViewFromObject(view: View, `object`: Any): Boolean = (view == `object`)

    override fun getCount(): Int = Pages.values().size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val context = container.context
        val customPagerEnum = Pages.values()[position]
        val layout: View = inflater.inflate(customPagerEnum.layout, container, false)
        container.addView(layout)
        when (DetailsPagerAdapter.Companion.Pages.values()[position]) {
            DetailsPagerAdapter.Companion.Pages.FILES -> {
                layout.files_list.let {
                    it.layoutManager = LinearLayoutManager(context)
                    it.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
                    it.adapter = filesAdapter
                }
            }
            DetailsPagerAdapter.Companion.Pages.HISTORY -> {
                layout.history_list.let {
                    it.layoutManager = LinearLayoutManager(context)
                    it.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
                    it.adapter = historyAdapter
                }
            }
        }
        return layout
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.getString(Pages.values()[position].stringRes)
    }

    fun setFilesList(list: List<GistFile>) {
        filesAdapter.setList(list)
    }

    fun setHistoryList(list: List<GistHistoryEntryModel>) {
        historyAdapter.setList(list)
    }

    companion object {
        enum class Pages(@LayoutRes val layout: Int, @StringRes val stringRes: Int) {
            FILES(R.layout.page_files, R.string.label_files),
            HISTORY(R.layout.page_commits, R.string.label_commits);
        }
    }
}
