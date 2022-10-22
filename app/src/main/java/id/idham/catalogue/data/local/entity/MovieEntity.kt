package id.idham.catalogue.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.idham.catalogue.ui.adapter.Equatable
import id.idham.catalogue.utils.DateUtils

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey var id: Int,
    var title: String?,
    var overview: String?,
    var releaseDate: String?,
    var rating: Double?,
    var lang: String?,
    var favorite: Boolean = false,
    var imagePath: String?
) : Equatable {
    fun getYearRelease() = releaseDate?.take(4)
    fun getFormattedDate() = DateUtils.getFullDateString(releaseDate)
    fun getScore() = "%.1f".format(rating)
}