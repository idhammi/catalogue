package id.idham.catalogue.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import id.idham.catalogue.data.local.entity.MovieEntity
import id.idham.catalogue.data.local.entity.TvShowEntity

class BaseItemCallback : DiffUtil.ItemCallback<Equatable>() {
    override fun areItemsTheSame(oldItem: Equatable, newItem: Equatable): Boolean {
        return when {
            oldItem is MovieEntity && newItem is MovieEntity -> oldItem.id == newItem.id
            oldItem is TvShowEntity && newItem is TvShowEntity -> oldItem.id == newItem.id
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: Equatable, newItem: Equatable): Boolean {
        return when {
            oldItem is MovieEntity && newItem is MovieEntity -> oldItem == newItem
            oldItem is TvShowEntity && newItem is TvShowEntity -> oldItem == newItem
            else -> false
        }
    }
}