package id.idham.catalogue.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.idham.catalogue.core.domain.model.Movie
import id.idham.catalogue.core.utils.ext.loadImage
import id.idham.catalogue.databinding.ItemMovieBinding

class MovieListAdapter(private val listener: (Movie?) -> Unit) :
    PagingDataAdapter<Movie, MovieListAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(listener, item)
        }
    }

    class ViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: (Movie?) -> Unit, item: Movie) {
            with(binding) {
                tvTitle.text = item.title
                tvYear.text = item.getYearRelease()
                itemView.setOnClickListener { listener(item) }
                imgPhoto.loadImage(item.posterPath)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.movieId == newItem.movieId
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}