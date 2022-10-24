package id.idham.catalogue.core.domain.repository

import androidx.paging.PagingData
import id.idham.catalogue.core.data.Resource
import id.idham.catalogue.core.domain.model.Movie
import id.idham.catalogue.core.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface ICatalogueRepository {

    fun getMovies(): Flow<PagingData<Movie>>

    fun getMovieDetail(id: Int): Flow<Resource<Movie?>>

    fun getTvShows(): Flow<PagingData<TvShow>>

    fun getTvShowDetail(id: Int): Flow<Resource<TvShow?>>

    fun getFavoriteMovies(sort: String): Flow<List<Movie>>

    fun setFavoriteMovie(movie: Movie, favorite: Boolean)

    fun getFavoriteTvShows(sort: String): Flow<List<TvShow>>

    fun setFavoriteTvShow(tvShow: TvShow, favorite: Boolean)

}