package id.idham.catalogue.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.PagingData
import id.idham.catalogue.data.local.entity.MovieEntity
import id.idham.catalogue.data.local.entity.TvShowEntity
import id.idham.catalogue.data.remote.response.MovieModel
import id.idham.catalogue.data.remote.response.TvShowModel
import id.idham.catalogue.vo.Pagination
import id.idham.catalogue.vo.Resource
import kotlinx.coroutines.flow.Flow

interface CatalogueDataSource {

    fun getMovies(): Flow<PagingData<MovieModel>>

    fun getMovieDetail(id: Int): Flow<ResourceFlow<MovieEntity>>

    fun getTvShows(): Pagination<TvShowModel>

    fun getTvShowDetail(id: Int): LiveData<Resource<TvShowEntity>>

    fun getFavoriteMovies(sort: String): LiveData<PagedList<MovieEntity>>

    fun setFavoriteMovie(movie: MovieEntity, favorite: Boolean)

    fun getFavoriteTvShows(sort: String): LiveData<PagedList<TvShowEntity>>

    fun setFavoriteTvShow(tvShow: TvShowEntity, favorite: Boolean)

}