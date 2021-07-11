package com.pricelinetest.features

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.pricelinetest.R
import com.pricelinetest.features.books.FragmentBooks
import com.pricelinetest.features.home.FragmentHome
import com.pricelinetest.models.Name

class ActivityMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState != null) return

        setHomeFragment()
    }

    private fun setHomeFragment() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, FragmentHome.newInstance())
            commitAllowingStateLoss()
        }
    }

    internal fun showNetworkErrorSnackBar() {
        showErrorSnackBar(getString(R.string.network_error))
    }

    internal fun showErrorSnackBar(errorMsg: String?) {
        Snackbar.make(
            findViewById(R.id.container),
            errorMsg ?: getString(R.string.error_msg),
            Snackbar.LENGTH_LONG
        ).show()
    }

    internal fun showBooks(nameItem: Name?) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, FragmentBooks.newInstance(nameItem))
            addToBackStack(null)
            commitAllowingStateLoss()
        }
    }
}