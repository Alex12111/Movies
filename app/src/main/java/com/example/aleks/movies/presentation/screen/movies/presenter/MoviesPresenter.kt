package com.example.aleks.movies.presentation.screen.movies.presenter

import android.util.Log
import com.example.aleks.movies.data.models.content.MoviesValue
import com.example.aleks.movies.presentation.screen.view.MoviesListView
import com.example.aleks.movies.repository.RepositoryProvider
import java.io.IOException

class MoviesPresenter(internal var mMoviesListView: MoviesListView?) {

    fun getListValues() {
        RepositoryProvider.provideMMRepository()
                .listValues
                .subscribe({ moviesRequest ->
                    mMoviesListView!!.setMoviesList(moviesRequest.moviesValues as MutableList<MoviesValue>)
                },
                        { throwable ->
                            Log.d(TAG, throwable.message)
                            if (throwable is IOException) {
                                mMoviesListView!!.showNetworkError()
                            } else {
                                mMoviesListView!!.showError()
                            }

                        })
    }

    companion object {

        private val TAG = MoviesPresenter::class.java.simpleName
    }
}
