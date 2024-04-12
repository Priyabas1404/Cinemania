package cinemania.cinemania.cinemania.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val FIRST_PAGE = 1
const val POST_PER_PAGE = 20

const val API_KEY = "4341cdf3d002895152f0161f7b2ad2e6"
const val BASE_URL = "https://api.themoviedb.org/3/"
const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"

object MovieDBClient {
    fun getClient(): MovieDBInterface {
        val requestInterceptor = Interceptor { chain ->

            val modifiedUrl = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()

            val modifiedRequest = chain.request()
                .newBuilder()
                .url(modifiedUrl)
                .build()

            return@Interceptor chain.proceed(modifiedRequest)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(MovieDBInterface::class.java)
    }
}