package id.idham.catalogue.core.data.remote.response

import com.squareup.moshi.Json

data class ListTvShowResponse(
    @Json(name = "results") val results: List<TvShowResponse>
)

data class TvShowResponse(
    @Json(name = "id") val id: Int?,
    @Json(name = "first_air_date") val firstAirDate: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "original_language") val originalLanguage: String?,
    @Json(name = "overview") val overview: String?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "vote_average") val voteAverage: Double?
)