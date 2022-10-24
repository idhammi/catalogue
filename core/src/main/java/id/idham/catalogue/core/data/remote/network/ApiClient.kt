package id.idham.catalogue.core.data.remote.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

const val apiKey = "ace75ede92ca77970a3c0be85357f2cf"
const val baseUrl = "https://api.themoviedb.org/3/"
const val imageUrl = "https://image.tmdb.org/t/p/original/"

fun providesApiKey(): Interceptor = Interceptor { chain ->
    var request: Request = chain.request()
    val url: HttpUrl = request.url.newBuilder()
        .addQueryParameter("api_key", apiKey)
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
    return OkHttpClient.Builder().apply {
        retryOnConnectionFailure(true)
        readTimeout(30, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
        addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        addInterceptor(providesApiKey())
        addInterceptor(provideChucker(context))
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
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshiConverter))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
        .create(ApiService::class.java)
}