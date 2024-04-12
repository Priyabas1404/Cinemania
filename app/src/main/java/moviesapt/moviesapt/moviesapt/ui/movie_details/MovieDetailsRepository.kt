package moviesapt.moviesapt.moviesapt.ui.movie_details

import androidx.lifecycle.LiveData
import moviesapt.moviesapt.moviesapt.data.api.MovieDBInterface
import moviesapt.moviesapt.moviesapt.data.repository.MovieDetailsNetworkDataSource
import moviesapt.moviesapt.moviesapt.data.repository.NetworkState
import moviesapt.moviesapt.moviesapt.data.vo.MovieDetails
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