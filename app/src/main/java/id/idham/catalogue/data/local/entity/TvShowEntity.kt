package id.idham.catalogue.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.idham.catalogue.utils.DateUtils

@Entity(tableName = "tvshow")
data class TvShowEntity(
    @PrimaryKey var id: Int,
    var name: String?,
    var overview: String?,
    var firstAirDate: String?,
    var rating: Double?,
    var lang: String?,
    var favorite: Boolean = false,
    var imagePath: String?
) {
    fun getYearRelease() = firstAirDate?.take(4)
    fun getFormattedDate() = DateUtils.getFullDateString(firstAirDate)
}