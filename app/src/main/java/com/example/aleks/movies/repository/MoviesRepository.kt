package com.example.aleks.movies.repository

import com.example.aleks.movies.data.models.content.Movies

import io.reactivex.Observable


interface MoviesRepository {
    val listValues: Observable<Movies>
}
