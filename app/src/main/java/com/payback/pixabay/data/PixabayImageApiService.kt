package com.payback.pixabay.data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.payback.pixabay.BuildConfig
import com.payback.pixabay.data.network.ConnectivityInterceptor
import com.payback.pixabay.response.ImageResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayImageApiService {

    /**
     * Searching photos by query, pages and items per page
     *
     * CLIENT_ID String api key (read README.MD)
     * @param query The String naming query
     * @param page The Int defining the number of page
     * @param perPage The Int defining the amount items per page (pagination purposes)
     */
    @GET("?key=$CLIENT_ID")
    suspend fun searchPhotos(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ) : ImageResponse

    companion object {
        private const val BASE_URL = "https://pixabay.com/api/"
        const val CLIENT_ID = BuildConfig.PIXABAY_ACCESS_KEY

        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): PixabayImageApiService {

            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url
                    .newBuilder()
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient
                .Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(PixabayImageApiService::class.java)
        }
    }
}