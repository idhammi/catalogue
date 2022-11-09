#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_id_idham_catalogue_core_utils_Config_getApiKey(JNIEnv *env, jobject object) {
    std::string api_key = "ace75ede92ca77970a3c0be85357f2cf";
    return env->NewStringUTF(api_key.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_id_idham_catalogue_core_utils_Config_getBaseUrl(JNIEnv *env, jobject object) {
    std::string base_url = "https://api.themoviedb.org/3/";
    return env->NewStringUTF(base_url.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_id_idham_catalogue_core_utils_Config_getImageUrl(JNIEnv *env, jobject object) {
    std::string image_url = "https://image.tmdb.org/t/p/original/";
    return env->NewStringUTF(image_url.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_id_idham_catalogue_core_utils_Config_getHostname(JNIEnv *env, jobject object) {
    std::string hostname = "api.themoviedb.org";
    return env->NewStringUTF(hostname.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_id_idham_catalogue_core_utils_Config_getApiCert1(JNIEnv *env, jobject object) {
    std::string api_cert1 = "sha256/p+WeEuGncQbjSKYPSzAaKpF/iLcOjFLuZubtsXupYSI=";
    return env->NewStringUTF(api_cert1.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_id_idham_catalogue_core_utils_Config_getApiCert2(JNIEnv *env, jobject object) {
    std::string api_cert2 = "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=";
    return env->NewStringUTF(api_cert2.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_id_idham_catalogue_core_utils_Config_getApiCert3(JNIEnv *env, jobject object) {
    std::string api_cert3 = "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=";
    return env->NewStringUTF(api_cert3.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_id_idham_catalogue_core_utils_Config_getDbPassphrase(JNIEnv *env, jobject object) {
    std::string db_passphrase = "movie_catalogue";
    return env->NewStringUTF(db_passphrase.c_str());
}