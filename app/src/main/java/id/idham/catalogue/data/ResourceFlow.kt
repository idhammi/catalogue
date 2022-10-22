package id.idham.catalogue.data

sealed class ResourceFlow<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : ResourceFlow<T>(data)
    class Loading<T>(data: T? = null) : ResourceFlow<T>(data)
    class Error<T>(message: String, data: T? = null) : ResourceFlow<T>(data, message)
}

