package id.idham.catalogue.core.data.remote.response

import com.squareup.moshi.Json

data class ListMovieResponse(
    @Json(name = "results") val results: List<MovieResponse>
)

data class MovieResponse(
    @Json(name = "id") val id: Int?,
    @Json(name = "original_language") val originalLanguage: String?,
    @Json(name = "overview") val overview: String?,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "release_date") val releaseDate: String?,
    @Json(name = "title") val title: String?,
    @Json(name = "vote_average") val voteAverage: Double?
)