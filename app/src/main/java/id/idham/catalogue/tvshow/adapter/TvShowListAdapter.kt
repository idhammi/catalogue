package id.idham.catalogue.tvshow.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.idham.catalogue.core.domain.model.TvShow
import id.idham.catalogue.core.utils.ext.loadImage
import id.idham.catalogue.databinding.ItemMovieBinding

class TvShowListAdapter(private val listener: (TvShow?) -> Unit) :
    PagingDataAdapter<TvShow, TvShowListAdapter.ViewHolder>(DIFF_CALLBACK) {

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
        fun bind(listener: (TvShow?) -> Unit, item: TvShow) {
            with(binding) {
                tvTitle.text = item.name
                tvYear.text = item.getYearRelease()
                itemView.setOnClickListener { listener(item) }
                imgPhoto.loadImage(item.posterPath)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShow>() {
            override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
                return oldItem.tvShowId == newItem.tvShowId
            }

            override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
                return oldItem == newItem
            }
        }
    }
}