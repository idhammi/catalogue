package id.idham.catalogue.core.data.mapper

import androidx.paging.PagingData
import androidx.paging.map
import id.idham.catalogue.core.data.local.entity.TvShowEntity
import id.idham.catalogue.core.data.remote.response.TvShowResponse
import id.idham.catalogue.core.domain.model.TvShow

object TvShowMapper {

    fun mapResponseToEntity(input: TvShowResponse) = TvShowEntity(
        tvShowId = input.id ?: 0,
        firstAirDate = input.firstAirDate.toString(),
        name = input.name.toString(),
        originalLanguage = input.originalLanguage.toString(),
        overview = input.overview.toString(),
        posterPath = input.posterPath.toString(),
        voteAverage = input.voteAverage ?: 0.0,
        isFavorite = false
    )

    fun mapEntityToDomain(input: TvShowEntity?) =
        if (input != null) TvShow(
            tvShowId = input.tvShowId,
            firstAirDate = input.firstAirDate,
            name = input.name,
            originalLanguage = input.originalLanguage,
            overview = input.overview,
            posterPath = input.posterPath,
            voteAverage = input.voteAverage,
            isFavorite = input.isFavorite
        ) else null

    fun mapEntityToDomain(input: List<TvShowEntity>) =
        input.map {
            TvShow(
                tvShowId = it.tvShowId,
                firstAirDate = it.firstAirDate,
                name = it.name,
                originalLanguage = it.originalLanguage,
                overview = it.overview,
                posterPath = it.posterPath,
                voteAverage = it.voteAverage,
                isFavorite = it.isFavorite
            )
        }

    fun mapResponseToDomain(input: PagingData<TvShowResponse>) =
        input.map {
            TvShow(
                tvShowId = it.id ?: 0,
                firstAirDate = it.firstAirDate.toString(),
                name = it.name.toString(),
                originalLanguage = it.originalLanguage.toString(),
                overview = it.overview.toString(),
                posterPath = it.posterPath.toString(),
                voteAverage = it.voteAverage ?: 0.0,
                isFavorite = false
            )
        }

    fun mapDomainToEntity(input: TvShow) = TvShowEntity(
        tvShowId = input.tvShowId,
        firstAirDate = input.firstAirDate,
        name = input.name,
        originalLanguage = input.originalLanguage,
        overview = input.overview,
        posterPath = input.posterPath,
        voteAverage = input.voteAverage,
        isFavorite = input.isFavorite
    )
}