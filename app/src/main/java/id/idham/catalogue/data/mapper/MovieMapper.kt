package id.idham.catalogue.data.mapper

import id.idham.catalogue.data.local.entity.MovieEntity
import id.idham.catalogue.data.remote.response.MovieModel

object MovieMapper {
    fun mapResponseToEntity(data: MovieModel) =
        MovieEntity(
            data.id,
            data.title,
            data.overview,
            data.releaseDate,
            data.voteAverage,
            data.originalLanguage,
            false,
            data.posterPath
        )
}