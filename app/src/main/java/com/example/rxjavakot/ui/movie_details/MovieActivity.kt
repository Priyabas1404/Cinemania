package com.example.rxjavakot.ui.movie_details

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.rxjavakot.data.api.MovieDBClient
import com.example.rxjavakot.data.api.MovieDBInterface
import com.example.rxjavakot.data.api.POSTER_BASE_URL
import com.example.rxjavakot.data.repository.NetworkState
import com.example.rxjavakot.data.repository.Status
import com.example.rxjavakot.data.vo.MovieDetails
import com.example.rxjavakot.databinding.ActivityMovieBinding

class MovieActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieDetailsViewModel
    lateinit var movieRepository: MovieDetailsRepository
    private lateinit var binding: ActivityMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val apiService : MovieDBInterface = MovieDBClient.getClient()
        movieRepository = MovieDetailsRepository(apiService)

        val movieId: Int = intent.getIntExtra("id",1)
        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            binding.progressBarPopular.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            binding.txtError.visibility = if (it.status == Status.FAILED) View.VISIBLE else View.GONE

        })

    }

    fun bindUI( it: MovieDetails){

        binding.movieTitle.text = it.title
        binding.movieReleaseDate.text = it.releaseDate
        binding.movieRating.text = it.rating.toString()
        binding.movieOverview.text = it.overview

        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(binding.ivMoviePoster);
    }

    private fun getViewModel(movieId: Int): MovieDetailsViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MovieDetailsViewModel(movieRepository, movieId) as T
            }
        }).get(MovieDetailsViewModel::class.java)
    }
}