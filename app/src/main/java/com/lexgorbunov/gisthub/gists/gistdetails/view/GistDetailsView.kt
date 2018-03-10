package com.lexgorbunov.gisthub.gists.gistdetails.view

import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.lexgorbunov.gisthub.R
import com.lexgorbunov.gisthub.app.utils.buildProgressDialog
import com.lexgorbunov.gisthub.app.utils.toast
import com.lexgorbunov.gisthub.app.utils.tryParseError
import com.lexgorbunov.gisthub.gists.entity.GistFile
import com.lexgorbunov.gisthub.gists.gistdetails.entity.GistHistoryEntryModel
import com.squareup.picasso.Picasso
import javax.inject.Inject

interface GistDetailsView {

    fun init(view: View)
    fun showError(throwable: Throwable)
    fun displayDetails(descr: String, filesList: List<GistFile>, historyList: List<GistHistoryEntryModel>)
    fun showAvatar(url: String)
    fun hideAvatar()
    fun showName(name: String)
    fun hideName()
    fun showProgress()
    fun hideProgress()
}

class GistDetailsViewImpl @Inject constructor() : GistDetailsView {

    private lateinit var view: View
    private lateinit var avatar: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var tabs: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var progressDialog: AlertDialog
    private lateinit var pagerAdapter: DetailsPagerAdapter

    override fun init(view: View) {
        this.view = view
        val context = view.context
        progressDialog = context.buildProgressDialog().create()
        avatar = view.findViewById(R.id.avatar)
        nameTextView = view.findViewById(R.id.name)
        descriptionTextView = view.findViewById(R.id.description)
        viewPager = view.findViewById(R.id.viewpager)
        pagerAdapter = DetailsPagerAdapter(context)
        viewPager.adapter = pagerAdapter
        tabs = view.findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }

    override fun showProgress() {
        progressDialog.show()
    }

    override fun hideProgress() {
        progressDialog.dismiss()
    }

    override fun displayDetails(descr: String, filesList: List<GistFile>, historyList: List<GistHistoryEntryModel>) {
        descriptionTextView.text = descr
        pagerAdapter.setFilesList(filesList)
        pagerAdapter.setHistoryList(historyList)
    }

    override fun showAvatar(url: String) {
        val avatarSize = avatar.context.resources.getDimensionPixelSize(R.dimen.gist_details_avatar_size)
        Picasso.with(avatar.context).load(url)
                .error(R.drawable.no_user_image)
                .resize(avatarSize, avatarSize)
                .onlyScaleDown()
                .into(avatar)
    }

    override fun hideAvatar() {
        avatar.setImageResource(R.drawable.no_user_image)
    }

    override fun showName(name: String) {
        nameTextView.text = name
        nameTextView.visibility = View.VISIBLE
    }

    override fun hideName() {
        nameTextView.visibility = View.GONE
    }

    override fun showError(throwable: Throwable) {
        tryParseError(view.context, throwable)?.let {
            view.context.toast(it.localizedMessage)
        }
    }
}
