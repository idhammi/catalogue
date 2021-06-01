package id.idham.catalogue.ui.tvshow

import id.idham.catalogue.data.source.local.entity.TvShowEntity

interface TvShowFragmentCallback {
    fun onShareClick(tvShow: TvShowEntity)
}
