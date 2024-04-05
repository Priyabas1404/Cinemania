package com.example.rxjavakot.ui.movie_details

import androidx.lifecycle.ViewModel
import com.example.rxjavakot.ui.movie_details.MovieDetailsRepository
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsViewModel(private val movieRepository : MovieDetailsRepository, movieId: Int) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  movieDetails by lazy {
        movieRepository.fetchSingleMovieDetails(compositeDisposable,movieId)
    }

    val networkState by lazy {
        movieRepository.getMovieDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}