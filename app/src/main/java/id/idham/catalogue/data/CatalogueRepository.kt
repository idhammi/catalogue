package id.idham.catalogue.data

import id.idham.catalogue.data.source.remote.RemoteRepository
import id.idham.catalogue.data.source.remote.response.MovieModel
import id.idham.catalogue.data.source.remote.response.MovieResponse
import id.idham.catalogue.data.source.remote.response.TvShowModel
import id.idham.catalogue.data.source.remote.response.TvShowResponse

class CatalogueRepository(private val remoteRepository: RemoteRepository) : Repository {

    override suspend fun getMovies(): MovieResponse {
        return remoteRepository.getMovies()
    }

    override suspend fun getMovieDetail(movieId: Int): MovieModel {
        return remoteRepository.getMovieDetail(movieId)
    }

    override suspend fun getTvShows(): TvShowResponse {
        return remoteRepository.getTvShows()
    }

    override suspend fun getTvShowDetail(tvId: Int): TvShowModel {
        return remoteRepository.getTvShowDetail(tvId)
    }

}