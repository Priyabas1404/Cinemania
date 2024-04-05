package com.example.rxjavakot.ui.movie_details

import androidx.lifecycle.LiveData
import com.example.rxjavakot.data.api.MovieDBInterface
import com.example.rxjavakot.data.repository.MovieDetailsNetworkDataSource
import com.example.rxjavakot.data.repository.NetworkState
import com.example.rxjavakot.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository  (private val apiService : MovieDBInterface) {

    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails (compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<MovieDetails> {

        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieResponse

    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }
}