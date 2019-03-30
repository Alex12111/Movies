package com.example.aleks.movies.repository

import com.example.aleks.movies.App
import com.example.aleks.movies.data.models.content.Movies

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DefaultMoviesRepository : MoviesRepository {

    override val listValues: Observable<Movies>
        get() = App.getmMoviesService()!!
                .movies
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { moviesResponse ->
                    App.getmMoviesService()
                    Observable.just(moviesResponse)
                }
}