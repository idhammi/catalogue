package id.idham.catalogue.data

import id.idham.catalogue.data.source.remote.response.MovieModel
import id.idham.catalogue.data.source.remote.response.MovieResponse
import id.idham.catalogue.data.source.remote.response.TvShowModel
import id.idham.catalogue.data.source.remote.response.TvShowResponse

interface Repository {

    suspend fun getMovies(): MovieResponse

    suspend fun getMovieDetail(movieId: Int): MovieModel

    suspend fun getTvShows(): TvShowResponse

    suspend fun getTvShowDetail(tvId: Int): TvShowModel

}