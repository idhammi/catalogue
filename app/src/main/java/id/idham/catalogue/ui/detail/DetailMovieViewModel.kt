package id.idham.catalogue.ui.detail

import androidx.lifecycle.ViewModel
import id.idham.catalogue.data.CatalogueRepository
import id.idham.catalogue.data.local.entity.MovieEntity
import id.idham.catalogue.data.local.entity.TvShowEntity

class DetailMovieViewModel(private val repository: CatalogueRepository) : ViewModel() {

    private var id: Int = 0

    fun setSelectedId(id: Int) {
        this.id = id
    }

    fun getMovie() = repository.getMovieDetail(id)

    fun getTvShow() = repository.getTvShowDetail(id)

    fun setFavoriteTvShow(tvShow: TvShowEntity) {
        val favorite = !tvShow.favorite
        repository.setFavoriteTvShow(tvShow, favorite)
    }

    fun setFavoriteMovie(movie: MovieEntity) {
        val favorite = !movie.favorite
        repository.setFavoriteMovie(movie, favorite)
    }

}