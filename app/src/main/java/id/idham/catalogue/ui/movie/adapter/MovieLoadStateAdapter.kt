package id.idham.catalogue.ui.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import id.idham.catalogue.R
import id.idham.catalogue.databinding.ItemMoviesLoadStateFooterBinding

class MovieLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<MovieLoadStateAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ViewHolder {
        return ViewHolder.create(parent, retry)
    }

    class ViewHolder(
        private val binding: ItemMoviesLoadStateFooterBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnMoviesRetry.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.tvMoviesErrorDescription.text = loadState.error.localizedMessage
            }
            binding.progressMoviesLoadMore.isVisible = loadState is LoadState.Loading
            binding.btnMoviesRetry.isVisible = loadState is LoadState.Error
            binding.tvMoviesErrorDescription.isVisible = loadState is LoadState.Error
        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): ViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_movies_load_state_footer, parent, false)
                val binding = ItemMoviesLoadStateFooterBinding.bind(view)
                return ViewHolder(binding, retry)
            }
        }
    }
}