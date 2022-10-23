package id.idham.catalogue.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import id.idham.catalogue.R
import id.idham.catalogue.databinding.ItemLoadStateFooterBinding

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
        private val binding: ItemLoadStateFooterBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnLoadRetry.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.tvLoadErrorDesc.text = loadState.error.localizedMessage
            }
            binding.pbLoadMore.isVisible = loadState is LoadState.Loading
            binding.btnLoadRetry.isVisible = loadState is LoadState.Error
            binding.tvLoadErrorDesc.isVisible = loadState is LoadState.Error
        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): ViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_load_state_footer, parent, false)
                val binding = ItemLoadStateFooterBinding.bind(view)
                return ViewHolder(binding, retry)
            }
        }
    }
}