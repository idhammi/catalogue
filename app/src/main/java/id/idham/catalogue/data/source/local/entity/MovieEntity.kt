package id.idham.catalogue.data.source.local.entity

import id.idham.catalogue.utils.DateUtils

data class MovieEntity(
    var id: Int,
    var title: String?,
    var overview: String?,
    var releaseDate: String?,
    var rating: Double?,
    var lang: String?,
    var bookmarked: Boolean = false,
    var imagePath: String?
) {
    fun getYearRelease() = releaseDate?.take(4)
    fun getFormattedDate() = DateUtils.getFullDateString(releaseDate)
}