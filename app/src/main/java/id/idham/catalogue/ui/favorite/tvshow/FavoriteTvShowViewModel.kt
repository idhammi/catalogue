package id.idham.catalogue.ui.favorite.tvshow

import androidx.lifecycle.ViewModel
import id.idham.catalogue.data.CatalogueDataSource

class FavoriteTvShowViewModel(private val catalogueDataSource: CatalogueDataSource) : ViewModel() {

    fun getFavoriteTvShows(sort: String, table: String) =
        catalogueDataSource.getFavoriteTvShows(sort, table)

}