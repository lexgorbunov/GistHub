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
        presenter.init(supportFragmentManager, savedInstanceState)
    }

    override fun onSupportNavigateUp(): Boolean {
        return if (/*backHandled() || */super.onSupportNavigateUp()) {
            true
        } else {
            super.onBackPressed()
            false
        }
    }

    /*private fun backHandled(): Boolean {
        val fragment = supportFragmentManager.primaryNavigationFragment
        return (fragment as? BackPressHandler)?.allowBackPressed() == true
    }*/

    /*override fun onBackPressed() {
        if (backHandled()) return
        super.onBackPressed()
    }*/

}
