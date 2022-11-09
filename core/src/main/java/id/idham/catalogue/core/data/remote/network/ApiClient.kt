package id.idham.catalogue.core.data.remote.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import id.idham.catalogue.core.BuildConfig
import id.idham.catalogue.core.utils.Config
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

fun provideHttpLoggingInterceptor() = run {
    HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }
}

fun providesApiKey(): Interceptor = Interceptor { chain ->
    var request: Request = chain.request()
    val url: HttpUrl = request.url.newBuilder()
        .addQueryParameter("api_key", Config.getApiKey())
        .build()
    request = request.newBuilder().url(url).build()
    chain.proceed(request)
}

fun provideChucker(context: Context) = run {
    ChuckerInterceptor.Builder(context)
        .collector(ChuckerCollector(context))
        .maxContentLength(250000L)
        .redactHeaders(emptySet())
        .alwaysReadResponseBody(false)
        .build()
}

fun providesHttpClient(context: Context): OkHttpClient {
    val certificatePinner = CertificatePinner.Builder()
        .add(Config.getHostname(), Config.getApiCert1())
        .add(Config.getHostname(), Config.getApiCert2())
        .add(Config.getHostname(), Config.getApiCert3())
        .build()
    return OkHttpClient.Builder().apply {
        retryOnConnectionFailure(true)
        readTimeout(30, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
        addInterceptor(provideHttpLoggingInterceptor())
        addInterceptor(providesApiKey())
        addInterceptor(provideChucker(context))
        certificatePinner(certificatePinner)
    }.build()
}

fun provideMoshiConverter(): Moshi = run {
    Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
}

fun provideApiClient(
    okHttpClient: OkHttpClient,
    moshiConverter: Moshi
): ApiService {
    return Retrofit.Builder()
        .baseUrl(Config.getBaseUrl())
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshiConverter))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
        .create(ApiService::class.java)
}