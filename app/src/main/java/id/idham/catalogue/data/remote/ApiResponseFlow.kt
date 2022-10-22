package id.idham.catalogue.data.remote

sealed class ApiResponseFlow<out R> {
    data class Success<out T>(val data: T) : ApiResponseFlow<T>()
    data class Error(val errorMessage: String) : ApiResponseFlow<Nothing>()
    object Empty : ApiResponseFlow<Nothing>()
}

