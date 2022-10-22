package id.idham.catalogue.ui.favorite.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.idham.catalogue.data.CatalogueRepository

class FavoriteMovieViewModel(private val repository: CatalogueRepository) : ViewModel() {

    fun getFavoriteMovies(sort: String) = repository.getFavoriteMovies(sort).asLiveData()

}