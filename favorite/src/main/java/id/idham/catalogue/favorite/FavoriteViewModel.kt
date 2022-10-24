package id.idham.catalogue.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.idham.catalogue.core.domain.usecase.CatalogueUseCase

class FavoriteViewModel(private val useCase: CatalogueUseCase) : ViewModel() {

    fun getFavoriteMovies(sort: String) = useCase.getFavoriteMovies(sort).asLiveData()

    fun getFavoriteTvShows(sort: String) = useCase.getFavoriteTvShows(sort).asLiveData()

}