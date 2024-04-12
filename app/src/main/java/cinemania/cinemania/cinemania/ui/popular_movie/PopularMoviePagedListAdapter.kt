package cinemania.cinemania.cinemania.ui.popular_movie

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import cinemania.cinemania.cinemania.data.api.POSTER_BASE_URL
import cinemania.cinemania.cinemania.data.repository.NetworkState
import cinemania.cinemania.cinemania.data.repository.Status
import cinemania.cinemania.cinemania.data.vo.Movie
import com.cinemania.cinemania.databinding.MovieListItemBinding
import com.cinemania.cinemania.databinding.NetworkStateItemBinding
import cinemania.cinemania.cinemania.ui.movie_details.MovieActivity

class PopularMoviePagedListAdapter (public val context: Context) :
    PagedListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffCallback()) {

    val MOVIE_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        if (viewType == MOVIE_VIEW_TYPE) {
            val binding = MovieListItemBinding.inflate(layoutInflater, parent, false)
            return MovieItemViewHolder(binding)
        } else {
            val binding = NetworkStateItemBinding.inflate(layoutInflater, parent, false)
            return NetworkStateItemViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MOVIE_VIEW_TYPE) {
            (holder as MovieItemViewHolder).bind(getItem(position),context)
        }
        else {
            (holder as NetworkStateItemViewHolder).bind(networkState)
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            NETWORK_VIEW_TYPE
        } else {
            MOVIE_VIEW_TYPE
        }
    }

    class MovieItemViewHolder(private val binding: MovieListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie?, context:Context) {
            binding.movieTitle.text = movie?.title
            binding.movieReleaseDate.text =  movie?.releaseDate
            val moviePosterURL = POSTER_BASE_URL + movie?.posterPath
            Glide.with(itemView.context)
                .load(moviePosterURL)
                .into(binding.ivMoviePoster);

            itemView.setOnClickListener{
                val intent = Intent(context, MovieActivity::class.java)
                intent.putExtra("id", movie?.id)
                context.startActivity(intent)
            }
        }
    }

    class NetworkStateItemViewHolder (private val binding: NetworkStateItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(networkState: NetworkState?) {
            if (networkState != null && networkState.status == Status.RUNNING) {
                binding.progressBar.visibility = View.VISIBLE;
            } else {
                binding.progressBar.visibility = View.GONE;
            }

            if (networkState != null && networkState.status == Status.FAILED) {
                binding.errorMsg.visibility = View.VISIBLE;
                binding.errorMsg.text = networkState.msg;
            } else {
                binding.errorMsg.visibility = View.GONE;
            }

        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        //check if the presence of an extra row has changed in comparison to previous and current network state
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) { //The extra row was present before the state change but is no longer needed after the change
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }
}