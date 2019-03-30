package com.example.aleks.movies.presentation.screen.movies.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.aleks.movies.R
import com.example.aleks.movies.presentation.screen.movies.fragments.MoviesListFragment
import com.example.aleks.movies.presentation.screen.view.Callbacks


class MoviesActivity : AppCompatActivity(), Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movies_activity)

        showSelectedFragment(MoviesListFragment())
    }

    override fun showSelectedFragment(selectedFragment: Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, selectedFragment)
                .commit()
    }

    override fun onBackPressed() {
        val fm = supportFragmentManager
        var backPressedListener: OnBackPressedListener? = null
        for (fragment in fm.fragments) {
            if (fragment is OnBackPressedListener) {
                backPressedListener = fragment
                break
            }
        }

        if (backPressedListener != null) {
            backPressedListener.onBackPressed()
        } else {
            super.onBackPressed()
        }
    }

    interface OnBackPressedListener {
        fun onBackPressed()
    }
}