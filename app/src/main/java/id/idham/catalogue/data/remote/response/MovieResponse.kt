package id.idham.catalogue.data.remote.response

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

data class MovieResponse(
    @Json(name = "page") val page: Int,
    @Json(name = "total_results") val totalResults: Int,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "results") val results: List<MovieModel>?
)

@Parcelize
data class MovieModel(
    @Json(name = "id") val id: Int,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "original_language") val originalLanguage: String?,
    @Json(name = "title") val title: String?,
    @Json(name = "vote_average") val voteAverage: Double?,
    @Json(name = "overview") val overview: String?,
    @Json(name = "release_date") val releaseDate: String?
) : Parcelable {
    fun getYearRelease() = releaseDate?.take(4)
}