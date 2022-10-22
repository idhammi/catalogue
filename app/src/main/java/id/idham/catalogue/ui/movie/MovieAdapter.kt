package id.idham.catalogue.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import id.idham.catalogue.BuildConfig
import id.idham.catalogue.data.remote.response.MovieModel
import id.idham.catalogue.databinding.ItemMovieBinding
import id.idham.catalogue.vo.Resource
import id.idham.catalogue.vo.Status

class MovieAdapter(private val listener: (MovieModel?) -> Unit) :
    PagedListAdapter<MovieModel, MovieAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieModel>() {
            override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var mNetworkState: Resource<MovieModel>? = null

    fun setNetworkState(networkState: Resource<MovieModel>?) {
        val hadExtraRow = hasExtraRow()
        mNetworkState = networkState
        if (hadExtraRow != hasExtraRow()) {
            if (!hasExtraRow()) {
                notifyItemRemoved(itemCount)
            } else {
                notifyItemInserted(itemCount)
            }
        } else if (hadExtraRow) {
            notifyItemChanged(itemCount - 1)
        }
    }

    private fun hasExtraRow() = mNetworkState != null && mNetworkState?.status != Status.SUCCESS

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
        fun bind(listener: (MovieModel?) -> Unit, item: MovieModel) {
            with(binding) {
                tvTitle.text = item.title
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