package com.chhotelalpal.themoviedb.api
import com.chhotelalpal.themoviedb.data.PopularMovies

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbEndpoints {

    @GET("/3/movie/popular")
    fun getPopular(@Query("api_key") key: String): Call<PopularMovies>

    @GET("/3/movie/top_rated")
    fun getTopRated(@Query("api_key") key: String): Call<PopularMovies>


    @GET("/3/movie/upcoming")
    fun getUpcoming(@Query("api_key") key: String): Call<PopularMovies>
}