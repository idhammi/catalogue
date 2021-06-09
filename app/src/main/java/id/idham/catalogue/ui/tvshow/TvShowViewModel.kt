package id.idham.catalogue.ui.tvshow

import androidx.lifecycle.ViewModel
import id.idham.catalogue.data.CatalogueDataSource

class TvShowViewModel(private val catalogueDataSource: CatalogueDataSource) : ViewModel() {

    fun getTvShows() = catalogueDataSource.getTvShows()

}