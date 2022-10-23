package id.idham.catalogue.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey @ColumnInfo(name = "movieId") var movieId: Int,
    @ColumnInfo(name = "originalLanguage") var originalLanguage: String,
    @ColumnInfo(name = "overview") var overview: String,
    @ColumnInfo(name = "posterPath") var posterPath: String,
    @ColumnInfo(name = "releaseDate") var releaseDate: String,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "voteAverage") var voteAverage: Double,
    @ColumnInfo(name = "isFavorite") var isFavorite: Boolean = false
)