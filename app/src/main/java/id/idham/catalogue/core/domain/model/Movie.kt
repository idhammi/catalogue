package id.idham.catalogue.core.domain.model

import id.idham.catalogue.core.ui.Equatable
import id.idham.catalogue.core.utils.DateUtils

data class Movie(
    val movieId: Int,
    val originalLanguage: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val isFavorite: Boolean
) : Equatable {
    fun getYearRelease() = releaseDate.take(4)
    fun getFormattedDate() = DateUtils.getFullDateString(releaseDate)
    fun getScore() = "%.1f".format(voteAverage)
}