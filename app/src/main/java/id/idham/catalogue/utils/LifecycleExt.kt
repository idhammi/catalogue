package id.idham.catalogue.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.*

fun <T> Fragment.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(viewLifecycleOwner, { action.invoke(it) })
}