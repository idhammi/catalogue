package id.idham.catalogue.vo

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class Pagination<T : Any>(
    val pagedList: LiveData<PagedList<T>>,
    val networkState: LiveData<Resource<T>>
)