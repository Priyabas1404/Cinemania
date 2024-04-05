package com.example.rxjavakot.data.api

import com.example.rxjavakot.data.vo.MovieDetails
import com.example.rxjavakot.data.vo.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDBInterface {

    //https://api.themoviedb.org/3/trending/movie/day?api_key=4341cdf3d002895152f0161f7b2ad2e6
    //https://api.themoviedb.org/3/movie/popular?api_key=4341cdf3d002895152f0161f7b2ad2e6
    //https://api.themoviedb.org/3/

    //Single is a in-built observable
    @GET("movie/popular")
    fun getPopularMovie(@Query("page")page:Int): Single<MovieResponse>

    @GET("movie/{movie_id}")  //upcoming , popular
    fun getMovieDetails(
        @Path("movie_id") id: Int
    ): Single<MovieDetails>
}