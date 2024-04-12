package cinemania.cinemania.cinemania.data.api

import cinemania.cinemania.cinemania.data.vo.MovieDetails
import cinemania.cinemania.cinemania.data.vo.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDBInterface {

    @GET("movie/popular")
    fun getPopularMovie(@Query("page")page:Int): Single<MovieResponse>

    @GET("movie/{movie_id}")  //upcoming , popular
    fun getMovieDetails(
        @Path("movie_id") id: Int
    ): Single<MovieDetails>
}