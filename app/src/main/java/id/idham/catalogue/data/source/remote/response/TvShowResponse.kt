package id.idham.catalogue.data.source.remote.response

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

data class TvShowResponse(
    @Json(name = "page") val page: String,
    @Json(name = "total_results") val totalResults: String,
    @Json(name = "total_pages") val totalPages: String,
    @Json(name = "results") val results: List<TvShowModel>?
)

@Parcelize
data class TvShowModel(
    @Json(name = "id") val id: Int,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "original_language") val originalLanguage: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "vote_average") val voteAverage: Double?,
    @Json(name = "overview") val overview: String?,
    @Json(name = "first_air_date") val firstAirDate: String?
) : Parcelable