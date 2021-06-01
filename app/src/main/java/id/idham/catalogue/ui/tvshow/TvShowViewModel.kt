package id.idham.catalogue.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.idham.catalogue.data.Repository
import id.idham.catalogue.data.source.remote.Resource
import id.idham.catalogue.data.source.remote.response.TvShowModel
import kotlinx.coroutines.launch

class TvShowViewModel(private val repository: Repository) : ViewModel() {

    private val _dataTvShows = MutableLiveData<Resource<List<TvShowModel>>>()
    val dataTvShows: LiveData<Resource<List<TvShowModel>>> = _dataTvShows

    fun getTvShows() {
        _dataTvShows.postValue(Resource.loading(null))
        viewModelScope.launch {
            try {
                val res = repository.getTvShows()
                _dataTvShows.postValue(Resource.success(res.results))
            } catch (t: Throwable) {
                _dataTvShows.postValue(Resource.error(t.message, null, t))
            }
        }
    }

}