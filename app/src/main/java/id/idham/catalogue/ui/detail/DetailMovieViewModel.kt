package id.idham.catalogue.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.idham.catalogue.data.CatalogueDataSource
import id.idham.catalogue.data.local.entity.MovieEntity
import id.idham.catalogue.data.local.entity.TvShowEntity
import id.idham.catalogue.data.remote.response.MovieModel
import id.idham.catalogue.data.remote.response.TvShowModel
import id.idham.catalogue.vo.Resource

class DetailMovieViewModel(private val catalogueDataSource: CatalogueDataSource) : ViewModel() {

    private val _dataMovie = MutableLiveData<Resource<MovieModel>>()
    val dataMovie: MutableLiveData<Resource<MovieModel>> = _dataMovie

    private val _dataTvShow = MutableLiveData<Resource<TvShowModel>>()
    val dataTvShow: MutableLiveData<Resource<TvShowModel>> = _dataTvShow

    private var id: Int = 0

    fun setSelectedId(id: Int) {
        this.id = id
    }

    fun getMovie() = catalogueDataSource.getMovieDetail(id)

    fun getTvShow() = catalogueDataSource.getTvShowDetail(id)

    fun setFavoriteTvShow(tvShow: TvShowEntity) {
        val favorite = !tvShow.favorite
        catalogueDataSource.setFavoriteTvShow(tvShow, favorite)
    }

    fun setFavoriteMovie(movie: MovieEntity) {
        val favorite = !movie.favorite
        catalogueDataSource.setFavoriteMovie(movie, favorite)
    }

}