package id.idham.catalogue.data.source.local.entity

import id.idham.catalogue.utils.DateUtils

data class TvShowEntity(
    var id: Int,
    var name: String?,
    var overview: String?,
    var firstAirDate: String?,
    var rating: Double?,
    var lang: String?,
    var bookmarked: Boolean = false,
    var imagePath: String?
) {
    fun getYearRelease() = firstAirDate?.take(4)
    fun getFormattedDate() = DateUtils.getFullDateString(firstAirDate)
}