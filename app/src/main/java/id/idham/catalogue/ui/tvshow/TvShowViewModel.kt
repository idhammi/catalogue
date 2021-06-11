package id.idham.catalogue.ui.tvshow

import androidx.lifecycle.ViewModel
import id.idham.catalogue.data.CatalogueRepository

class TvShowViewModel(private val repository: CatalogueRepository) : ViewModel() {

    fun getTvShows() = repository.getTvShows()

}