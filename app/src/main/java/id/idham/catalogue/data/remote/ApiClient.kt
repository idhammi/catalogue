package id.idham.catalogue.data.remote

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import id.idham.catalogue.BuildConfig
import id.idham.catalogue.BuildConfig.apiKey
import id.idham.catalogue.BuildConfig.baseUrl
import id.idham.catalogue.data.remote.endpoint.ApiService
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

fun provideHttpLoggingInterceptor() = run {
    HttpLoggingInterceptor().apply {
        apply { level = HttpLoggingInterceptor.Level.BODY }
    }
}

fun providesApiKey(): Interceptor = Interceptor { chain ->
    var request: Request = chain.request()
    val url: HttpUrl = request.url.newBuilder()
        .addQueryParameter("api_key", apiKey)
        .build()
    request = request.newBuilder().url(url).build()
    chain.proceed(request)
}

fun providesHttpClient(context: Context): OkHttpClient {
    return OkHttpClient.Builder().apply {
        retryOnConnectionFailure(true)
        readTimeout(30, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
        addInterceptor(providesApiKey())
        addInterceptor(provideHttpLoggingInterceptor())
        if (BuildConfig.DEBUG) addInterceptor(ChuckerInterceptor(context))
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