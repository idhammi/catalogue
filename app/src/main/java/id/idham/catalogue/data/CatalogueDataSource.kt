package id.idham.catalogue.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import id.idham.catalogue.data.local.entity.MovieEntity
import id.idham.catalogue.data.local.entity.TvShowEntity
import id.idham.catalogue.data.remote.response.MovieModel
import id.idham.catalogue.data.remote.response.TvShowModel
import id.idham.catalogue.vo.Pagination
import id.idham.catalogue.vo.Resource

interface CatalogueDataSource {

    fun getMovies(): Pagination<MovieModel>

    fun getMovieDetail(id: Int): LiveData<Resource<MovieEntity>>

    fun getTvShows(): Pagination<TvShowModel>

    fun getTvShowDetail(id: Int): LiveData<Resource<TvShowEntity>>

    fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>>

    fun setFavoriteMovie(movie: MovieEntity, favorite: Boolean)

    fun getFavoriteTvShows(): LiveData<PagedList<TvShowEntity>>

    fun setFavoriteTvShow(tvShow: TvShowEntity, favorite: Boolean)

}