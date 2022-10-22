package id.idham.catalogue.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.idham.catalogue.data.CatalogueRepository

class FavoriteViewModel(private val repository: CatalogueRepository) : ViewModel() {

    fun getFavoriteMovies(sort: String) = repository.getFavoriteMovies(sort).asLiveData()

    fun getFavoriteTvShows(sort: String) = repository.getFavoriteTvShows(sort).asLiveData()

}