package id.idham.catalogue.ui.favorite.tvshow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import id.idham.catalogue.BuildConfig
import id.idham.catalogue.data.local.entity.TvShowEntity
import id.idham.catalogue.databinding.ItemsTvShowBinding
import id.idham.catalogue.vo.Resource
import id.idham.catalogue.vo.Status

class FavoriteTvShowAdapter(private val listener: (TvShowEntity?) -> Unit) :
    PagedListAdapter<TvShowEntity, FavoriteTvShowAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowEntity>() {
            override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var mNetworkState: Resource<TvShowEntity>? = null

    fun setNetworkState(networkState: Resource<TvShowEntity>?) {
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
        val binding = ItemsTvShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(listener, item)
        }
    }

    class ViewHolder(private val binding: ItemsTvShowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: (TvShowEntity?) -> Unit, item: TvShowEntity) {
            with(binding) {
                tvTitle.text = item.name
                tvYear.text = item.getYearRelease()
                itemView.setOnClickListener { listener(item) }
                Glide.with(itemView.context)
                    .load(BuildConfig.imageUrl + item.imagePath)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgPhoto)
            }
        }
    }
}