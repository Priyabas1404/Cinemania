package com.example.rxjavakot.ui.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.rxjavakot.data.api.MovieDBInterface
import com.example.rxjavakot.data.api.POST_PER_PAGE
import com.example.rxjavakot.data.repository.MovieDataSource
import com.example.rxjavakot.data.repository.MovieDataSourceFactory
import com.example.rxjavakot.data.repository.NetworkState
import com.example.rxjavakot.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepository (private val apiService : MovieDBInterface){

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var movieDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable): LiveData<PagedList<Movie>> {
        movieDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(movieDataSourceFactory, config).build()

        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return movieDataSourceFactory.movieLiveDataSource.switchMap { dataSource ->
            dataSource.networkState
        }
    }
}