package com.example.aleks.movies.presentation.screen.view

import com.example.aleks.movies.data.models.content.MoviesValue


interface MoviesListView {

    fun setMoviesList(moviesList: MutableList<MoviesValue>)

    fun showError()

    fun showNetworkError()
}
