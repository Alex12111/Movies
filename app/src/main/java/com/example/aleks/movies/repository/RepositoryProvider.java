package com.example.aleks.movies.repository;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

public final class RepositoryProvider {

    private static MoviesRepository moviesRepository;

    private RepositoryProvider(){
    }

    @NonNull
    public static MoviesRepository provideMMRepository(){
        if (moviesRepository == null){
            moviesRepository = new DefaultMoviesRepository();
        }
        return moviesRepository;
    }

    @MainThread
    public static void init(){
        moviesRepository = new DefaultMoviesRepository();
    }
}