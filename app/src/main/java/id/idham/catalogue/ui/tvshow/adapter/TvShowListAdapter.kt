package id.idham.catalogue.ui.tvshow.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import id.idham.catalogue.BuildConfig
import id.idham.catalogue.data.remote.response.TvShowModel
import id.idham.catalogue.databinding.ItemMovieBinding

class TvShowListAdapter(private val listener: (TvShowModel?) -> Unit) :
    PagingDataAdapter<TvShowModel, TvShowListAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowModel>() {
            override fun areItemsTheSame(oldItem: TvShowModel, newItem: TvShowModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShowModel, newItem: TvShowModel): Boolean {
                return oldItem == newItem
            }
        }
    }

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
        fun bind(listener: (TvShowModel?) -> Unit, item: TvShowModel) {
            with(binding) {
                tvTitle.text = item.name
                tvYear.text = item.getYearRelease()
                itemView.setOnClickListener { listener(item) }
                Glide.with(itemView.context)
                    .load(BuildConfig.imageUrl + item.posterPath)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgPhoto)
            }
        }
    }
}