package id.idham.catalogue.core.data.mapper

import androidx.paging.PagingData
import androidx.paging.map
import id.idham.catalogue.core.data.local.entity.MovieEntity
import id.idham.catalogue.core.data.remote.response.MovieResponse
import id.idham.catalogue.core.domain.model.Movie

object MovieMapper {

    fun mapResponseToEntity(input: MovieResponse) = MovieEntity(
        movieId = input.id ?: 0,
        originalLanguage = input.originalLanguage.toString(),
        overview = input.overview.toString(),
        posterPath = input.posterPath.toString(),
        releaseDate = input.releaseDate.toString(),
        title = input.title.toString(),
        voteAverage = input.voteAverage ?: 0.0,
        isFavorite = false
    )

    fun mapEntityToDomain(input: MovieEntity?) =
        if (input != null) Movie(
            movieId = input.movieId,
            originalLanguage = input.originalLanguage,
            overview = input.overview,
            posterPath = input.posterPath,
            releaseDate = input.releaseDate,
            title = input.title,
            voteAverage = input.voteAverage,
            isFavorite = input.isFavorite
        ) else null

    fun mapEntityToDomain(input: List<MovieEntity>) =
        input.map {
            Movie(
                movieId = it.movieId,
                originalLanguage = it.originalLanguage,
                overview = it.overview,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                title = it.title,
                isFavorite = it.isFavorite
            )
        }

    fun mapResponseToDomain(input: PagingData<MovieResponse>) =
        input.map {
            Movie(
                movieId = it.id ?: 0,
                originalLanguage = it.originalLanguage.toString(),
                overview = it.overview.toString(),
                posterPath = it.posterPath.toString(),
                releaseDate = it.releaseDate.toString(),
                voteAverage = it.voteAverage ?: 0.0,
                title = it.title.toString(),
                isFavorite = false
            )
        }

    fun mapDomainToEntity(input: Movie) = MovieEntity(
        movieId = input.movieId,
        originalLanguage = input.originalLanguage,
        overview = input.overview,
        posterPath = input.posterPath,
        releaseDate = input.releaseDate,
        title = input.title,
        voteAverage = input.voteAverage,
        isFavorite = input.isFavorite
    )
}