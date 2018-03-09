package com.lexgorbunov.gisthub.gists.gistdetails.view

import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.lexgorbunov.gisthub.R
import com.lexgorbunov.gisthub.app.utils.buildProgressDialog
import com.lexgorbunov.gisthub.app.utils.toast
import com.lexgorbunov.gisthub.app.utils.tryParseError
import com.lexgorbunov.gisthub.gists.entity.Gist
import com.lexgorbunov.gisthub.gists.gistdetails.view.files.GetGistFileTitleByPosHandler
import com.lexgorbunov.gisthub.gists.gistdetails.view.files.GistFilesAdapter
import com.lexgorbunov.gisthub.gists.gistdetails.view.files.GistFilesDecoration
import com.lexgorbunov.gisthub.gists.gistdetails.view.history.GistHistoryAdapter
import com.squareup.picasso.Picasso
import javax.inject.Inject

interface GistDetailsView {

    fun init(view: View)
    fun showError(throwable: Throwable)
    fun displayDetails(gist: Gist)
    fun toggleProgress(isProgress: Boolean)

}

class GistDetailsViewImpl @Inject constructor(private val filesAdapter: GistFilesAdapter,
                                              private val historyAdapter: GistHistoryAdapter) : GistDetailsView,
        OnPageInitiated, GetGistFileTitleByPosHandler {

    private lateinit var view: View
    private lateinit var avatar: ImageView
    private lateinit var name: TextView
    private lateinit var description: TextView
    private lateinit var tabs: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var progressDialog: AlertDialog

    override fun init(view: View) {
        this.view = view
        val context = view.context
        progressDialog = context.buildProgressDialog().create()
        avatar = view.findViewById(R.id.avatar)
        name = view.findViewById(R.id.name)
        description = view.findViewById(R.id.description)
        viewPager = view.findViewById(R.id.viewpager)
        viewPager.adapter = DetailsPagerAdapter(context, this)
        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {

            }
        })
        tabs = view.findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }

    override fun onPageInit(v: View, position: Int) {
        when (DetailsPagerAdapter.Companion.Pages.values()[position]) {
            DetailsPagerAdapter.Companion.Pages.FILES -> {
                v.findViewById<RecyclerView>(R.id.files_list).let {
                    it.layoutManager = LinearLayoutManager(v.context)
                    it.addItemDecoration(GistFilesDecoration(v.context, this))
                    it.adapter = filesAdapter
                }
            }
            DetailsPagerAdapter.Companion.Pages.HISTORY -> {
                v.findViewById<RecyclerView>(R.id.history_list).let {
                    it.layoutManager = LinearLayoutManager(v.context)
                    it.addItemDecoration(DividerItemDecoration(v.context, DividerItemDecoration.VERTICAL))
                    it.adapter = historyAdapter
                }
            }
        }
    }

    override fun getTitleByPos(pos: Int): String? = filesAdapter.getGistFileByPos(pos)?.fileName

    override fun toggleProgress(isProgress: Boolean) {
        if (isProgress) progressDialog.show() else progressDialog.dismiss()
    }

    override fun displayDetails(gist: Gist) {
        description.text = gist.description ?: ""
        if (gist.owner?.avatarUrl.isNullOrBlank()) {
            avatar.setImageResource(R.drawable.no_user_image)
            name.visibility = View.GONE
        } else {
            name.visibility = View.VISIBLE
            name.text = gist.owner!!.login ?: ""
            val avatarSize = avatar.context.resources.getDimensionPixelSize(R.dimen.gist_details_avatar_size)
            Picasso.with(avatar.context).load(gist.owner.avatarUrl)
                    .error(R.drawable.no_user_image)
                    .resize(avatarSize, avatarSize)
                    .onlyScaleDown()
                    .into(avatar)
        }
        gist.files?.let {
            filesAdapter.setList(it.values.toList())
        }
        gist.history?.let {
            historyAdapter.setList(it)
        }
    }

    override fun showError(throwable: Throwable) {
        tryParseError(view.context, throwable)?.let {
            view.context.toast(it.localizedMessage)
        }
    }

}
