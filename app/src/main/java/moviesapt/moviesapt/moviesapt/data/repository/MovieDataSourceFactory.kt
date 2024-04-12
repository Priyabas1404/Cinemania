package moviesapt.moviesapt.moviesapt.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import moviesapt.moviesapt.moviesapt.data.api.MovieDBInterface
import moviesapt.moviesapt.moviesapt.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(
    private val apiService: MovieDBInterface,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Movie>() {

    val movieLiveDataSource = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService, compositeDisposable)

        movieLiveDataSource.postValue(movieDataSource)
        return  movieDataSource
    }
}