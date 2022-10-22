package id.idham.catalogue.data

import androidx.paging.PagingData
import id.idham.catalogue.data.local.entity.MovieEntity
import id.idham.catalogue.data.local.entity.TvShowEntity
import id.idham.catalogue.data.remote.response.MovieModel
import id.idham.catalogue.data.remote.response.TvShowModel
import kotlinx.coroutines.flow.Flow

interface CatalogueDataSource {

    fun getMovies(): Flow<PagingData<MovieModel>>

    fun getMovieDetail(id: Int): Flow<Resource<MovieEntity>>

    fun getTvShows(): Flow<PagingData<TvShowModel>>

    fun getTvShowDetail(id: Int): Flow<Resource<TvShowEntity>>

    fun getFavoriteMovies(sort: String): Flow<List<MovieEntity>>

    fun setFavoriteMovie(movie: MovieEntity, favorite: Boolean)

    fun getFavoriteTvShows(sort: String): Flow<List<TvShowEntity>>

    fun setFavoriteTvShow(tvShow: TvShowEntity, favorite: Boolean)

}