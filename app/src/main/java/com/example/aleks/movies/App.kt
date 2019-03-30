package com.example.aleks.movies

import android.app.Application

import com.example.aleks.movies.data.network.MoviesService

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {
    private var mRetrofit: Retrofit? = null

    override fun onCreate() {
        super.onCreate()
        mRetrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        mMoviesService = mRetrofit!!.create(MoviesService::class.java)
    }

    companion object {
        private var mMoviesService: MoviesService? = null
        private val BASE_URL = "https://demo0216585.mockable.io"

        fun getmMoviesService(): MoviesService? {
            return mMoviesService
        }
    }
}
