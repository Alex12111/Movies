package com.example.aleks.movies.data.models.content

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MoviesValue {

    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("year")
    @Expose
    var year: Int? = null
    @SerializedName("genre")
    @Expose
    var genre: List<String>? = null
    @SerializedName("director")
    @Expose
    var director: String? = null
    @SerializedName("desription")
    @Expose
    var desription: String? = null
    @SerializedName("image")
    @Expose
    var image: String? = null
}
