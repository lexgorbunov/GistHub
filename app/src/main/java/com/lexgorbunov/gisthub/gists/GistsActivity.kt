package com.lexgorbunov.gisthub.gists

import android.os.Bundle
import com.lexgorbunov.gisthub.R
import com.lexgorbunov.gisthub.gists.presenter.GistPresenter
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_gists.*
import javax.inject.Inject

class GistActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var presenter: GistPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gists)
        setSupportActionBar(toolbar)
        presenter.init(savedInstanceState)
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return false
    }

}
