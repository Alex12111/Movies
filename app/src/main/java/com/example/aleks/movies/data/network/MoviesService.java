package com.example.aleks.movies.data.network;

import com.example.aleks.movies.data.models.content.Movies;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MoviesService {

    @GET("movies")
    Observable<Movies> getMovies();
}
