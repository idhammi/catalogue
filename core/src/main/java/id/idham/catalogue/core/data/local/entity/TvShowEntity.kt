package id.idham.catalogue.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tvshow")
data class TvShowEntity(
    @PrimaryKey @ColumnInfo(name = "tvShowId") var tvShowId: Int,
    @ColumnInfo(name = "firstAirDate") var firstAirDate: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "originalLanguage") var originalLanguage: String,
    @ColumnInfo(name = "overview") var overview: String,
    @ColumnInfo(name = "posterPath") var posterPath: String,
    @ColumnInfo(name = "voteAverage") var voteAverage: Double,
    @ColumnInfo(name = "isFavorite") var isFavorite: Boolean = false
)