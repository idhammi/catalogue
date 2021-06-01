package id.idham.catalogue.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.idham.catalogue.data.Repository
import id.idham.catalogue.data.source.remote.Resource
import id.idham.catalogue.data.source.remote.response.MovieModel
import id.idham.catalogue.data.source.remote.response.TvShowModel
import kotlinx.coroutines.launch

class DetailMovieViewModel(private val repository: Repository) : ViewModel() {

    private val _dataMovie = MutableLiveData<Resource<MovieModel>>()
    val dataMovie: MutableLiveData<Resource<MovieModel>> = _dataMovie

    private val _dataTvShow = MutableLiveData<Resource<TvShowModel>>()
    val dataTvShow: MutableLiveData<Resource<TvShowModel>> = _dataTvShow

    private lateinit var id: String

    fun setSelectedId(id: String) {
        this.id = id
    }

    fun getMovie() {
        _dataMovie.postValue(Resource.loading())
        viewModelScope.launch {
            try {
                val res = repository.getMovieDetail(id.toInt())
                _dataMovie.postValue(Resource.success(res))
            } catch (t: Throwable) {
                _dataMovie.postValue(Resource.error(t.message, null, t))
            }
        }
    }

    fun getTvShow() {
        _dataTvShow.postValue(Resource.loading())
        viewModelScope.launch {
            try {
                val res = repository.getTvShowDetail(id.toInt())
                _dataTvShow.postValue(Resource.success(res))
            } catch (t: Throwable) {
                _dataTvShow.postValue(Resource.error(t.message, null, t))
            }
        }
    }
}