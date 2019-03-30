package com.example.aleks.movies.data.models.content

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Movies {

    @SerializedName("values")
    @Expose
    var moviesValues: List<MoviesValue>? = null

}