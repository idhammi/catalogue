package id.idham.catalogue.data.mapper

import id.idham.catalogue.data.local.entity.TvShowEntity
import id.idham.catalogue.data.remote.response.TvShowModel

object TvShowMapper {
    fun mapEntityToResponse(data: TvShowEntity) =
        TvShowModel(
            data.id,
            data.imagePath,
            data.lang,
            data.name,
            data.rating,
            data.overview,
            data.firstAirDate
        )

    fun mapResponseToEntity(data: TvShowModel) =
        TvShowEntity(
            data.id,
            data.name,
            data.overview,
            data.firstAirDate,
            data.voteAverage,
            data.originalLanguage,
            false,
            data.posterPath
        )
}