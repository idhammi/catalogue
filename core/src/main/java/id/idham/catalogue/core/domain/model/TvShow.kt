package id.idham.catalogue.core.domain.model

import id.idham.catalogue.core.ui.Equatable
import id.idham.catalogue.core.utils.DateUtils

data class TvShow(
    val tvShowId: Int,
    val firstAirDate: String,
    val name: String,
    val originalLanguage: String,
    val overview: String,
    val posterPath: String,
    val voteAverage: Double,
    val isFavorite: Boolean
) : Equatable {
    fun getYearRelease() = firstAirDate.take(4)
    fun getFormattedDate() = DateUtils.getFullDateString(firstAirDate)
    fun getScore() = "%.1f".format(voteAverage)
}