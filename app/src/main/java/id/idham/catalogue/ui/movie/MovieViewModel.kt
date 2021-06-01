package id.idham.catalogue.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.idham.catalogue.data.Repository
import id.idham.catalogue.data.source.remote.Resource
import id.idham.catalogue.data.source.remote.response.MovieModel
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: Repository) : ViewModel() {

    private val _dataMovies = MutableLiveData<Resource<List<MovieModel>>>()
    val dataMovies: LiveData<Resource<List<MovieModel>>> = _dataMovies

    fun getMovies() {
        _dataMovies.postValue(Resource.loading())
        viewModelScope.launch {
            try {
                val res = repository.getMovies()
                _dataMovies.postValue(Resource.success(res.results))
            } catch (t: Throwable) {
                _dataMovies.postValue(Resource.error(t.message, null, t))
            }
        }
    }

}