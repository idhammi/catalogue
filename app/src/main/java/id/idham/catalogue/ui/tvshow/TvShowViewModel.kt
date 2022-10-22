package id.idham.catalogue.ui.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import id.idham.catalogue.data.CatalogueRepository

class TvShowViewModel(private val repository: CatalogueRepository) : ViewModel() {

    fun getTvShows() = repository.getTvShows().cachedIn(viewModelScope)

}