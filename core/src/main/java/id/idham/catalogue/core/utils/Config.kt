package id.idham.catalogue.core.utils

object Config {

    init {
        System.loadLibrary("native-lib")
    }

    external fun getApiKey(): String
    external fun getBaseUrl(): String
    external fun getImageUrl(): String
    external fun getHostname(): String
    external fun getApiCert1(): String
    external fun getApiCert2(): String
    external fun getApiCert3(): String
    external fun getDbPassphrase(): String

}