package id.idham.catalogue.ui.favorite.tvshow

import androidx.lifecycle.ViewModel
import id.idham.catalogue.data.CatalogueRepository

class FavoriteTvShowViewModel(private val repository: CatalogueRepository) : ViewModel() {

    fun getFavoriteTvShows(sort: String) = repository.getFavoriteTvShows(sort)

}