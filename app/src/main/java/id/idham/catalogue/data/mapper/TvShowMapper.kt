package id.idham.catalogue.data.mapper

import id.idham.catalogue.data.local.entity.TvShowEntity
import id.idham.catalogue.data.remote.response.TvShowModel

object TvShowMapper {
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