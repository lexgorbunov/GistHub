package com.lexgorbunov.gisthub.gists.gistdetails

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lexgorbunov.gisthub.R
import com.lexgorbunov.gisthub.gists.gistdetails.presenter.GistDetailsPresenter
import com.lexgorbunov.gisthub.gists.gistdetails.view.GistDetailsView
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_gist_details.*
import javax.inject.Inject

class GistDetailsFragment : DaggerFragment() {

    @Inject
    lateinit var presenter: GistDetailsPresenter
    @Inject
    lateinit var detailsView: GistDetailsView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_gist_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsView.init(gist_details_container)
        presenter.init(arguments!!.getString(ARG_GIST_ID), detailsView, fragmentManager!!)
    }

    override fun onResume() {
        super.onResume()
        with((activity as AppCompatActivity)) {
            setTitle(R.string.title_gist_details)
            with(supportActionBar) {
                if (this != null) {
                    setDisplayHomeAsUpEnabled(true)
                    setDisplayShowHomeEnabled(true)
                }
            }
        }
    }

    companion object {

        private const val ARG_GIST_ID = "arg_gist_id"

        fun getInstance(gistId: String): GistDetailsFragment {
            return GistDetailsFragment().let {
                it.arguments = with(Bundle()) {
                    putString(ARG_GIST_ID, gistId)
                    this
                }
                it
            }
        }

    }

}
