package com.lexgorbunov.gisthub.gists.gistdetails.view

import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.lexgorbunov.gisthub.R
import com.lexgorbunov.gisthub.app.utils.toast
import com.lexgorbunov.gisthub.app.utils.tryParseError
import com.lexgorbunov.gisthub.gists.entity.Gist
import com.squareup.picasso.Picasso
import javax.inject.Inject

interface GistDetailsView {

    fun init(view: View)

    //fun showToast(text: String)
    fun showError(throwable: Throwable)

    fun displayDetails(gist: Gist)
}

class GistDetailsViewImpl @Inject constructor(private val filesAdapter: GistFilesAdapter) : GistDetailsView,
        OnPageInitiated, GetGistFileTitleByPosHandler {

    private lateinit var view: View
    private lateinit var avatar: ImageView
    private lateinit var name: TextView
    private lateinit var description: TextView
    private lateinit var tabs: TabLayout
    private lateinit var viewPager: ViewPager

    override fun init(view: View) {
        this.view = view
        avatar = view.findViewById(R.id.avatar)
        name = view.findViewById(R.id.name)
        description = view.findViewById(R.id.description)
        viewPager = view.findViewById(R.id.viewpager)
        viewPager.adapter = DetailsPagerAdapter(view.context, filesAdapter, this)
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
                    it.adapter = filesAdapter
                    it.addItemDecoration(GistFilesDecoration(v.context, this))
                }
            }
            DetailsPagerAdapter.Companion.Pages.HISTORY -> {
                v.findViewById<RecyclerView>(R.id.history_list).let {
//                    it.layoutManager = LinearLayoutManager(v.context)
//                    it.addItemDecoration(GistFilesDecoration(v.context))
                }
            }
        }
    }

    override fun getTitleByPos(pos: Int): String? = filesAdapter.getGistFileByPos(pos)?.fileName

    //override fun showToast(text: String) {
    //    view.context.toast(text)
    //}

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
    }

    override fun showError(throwable: Throwable) {
        tryParseError(view.context, throwable)?.let {
            view.context.toast(it.localizedMessage)
        }
    }

}
