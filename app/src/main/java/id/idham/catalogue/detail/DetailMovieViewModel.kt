package id.idham.catalogue.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.idham.catalogue.core.domain.model.Movie
import id.idham.catalogue.core.domain.model.TvShow
import id.idham.catalogue.core.domain.usecase.CatalogueUseCase

class DetailMovieViewModel(private val useCase: CatalogueUseCase) : ViewModel() {

    private var id: Int = 0

    fun setSelectedId(id: Int) {
        this.id = id
    }

    fun getMovie() = useCase.getMovieDetail(id).asLiveData()

    fun getTvShow() = useCase.getTvShowDetail(id).asLiveData()

    fun setFavoriteTvShow(tvShow: TvShow) {
        val favorite = !tvShow.isFavorite
        useCase.setFavoriteTvShow(tvShow, favorite)
    }

    fun setFavoriteMovie(movie: Movie) {
        val favorite = !movie.isFavorite
        useCase.setFavoriteMovie(movie, favorite)
    }

}