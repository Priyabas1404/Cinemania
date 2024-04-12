package cinemania.cinemania.cinemania.ui.movie_details

import androidx.lifecycle.LiveData
import cinemania.cinemania.cinemania.data.api.MovieDBInterface
import cinemania.cinemania.cinemania.data.repository.MovieDetailsNetworkDataSource
import cinemania.cinemania.cinemania.data.repository.NetworkState
import cinemania.cinemania.cinemania.data.vo.MovieDetails
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