package id.idham.catalogue.core.ui

import androidx.recyclerview.widget.DiffUtil
import id.idham.catalogue.core.domain.model.Movie
import id.idham.catalogue.core.domain.model.TvShow

class BaseItemCallback : DiffUtil.ItemCallback<Equatable>() {
    override fun areItemsTheSame(oldItem: Equatable, newItem: Equatable): Boolean {
        return when {
            oldItem is Movie && newItem is Movie -> oldItem.movieId == newItem.movieId
            oldItem is TvShow && newItem is TvShow -> oldItem.tvShowId == newItem.tvShowId
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: Equatable, newItem: Equatable): Boolean {
        return when {
            oldItem is Movie && newItem is Movie -> oldItem == newItem
            oldItem is TvShow && newItem is TvShow -> oldItem == newItem
            else -> false
        }
    }
}