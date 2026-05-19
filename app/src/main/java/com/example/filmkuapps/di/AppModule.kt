package com.example.filmkuapps.di

import android.util.Log
import com.example.filmkuapps.data.remote.api.ApiPath.BASE_URL
import com.example.filmkuapps.data.remote.api.ApiService
import com.example.filmkuapps.data.repository.MovieRepositoryImpl
import com.example.filmkuapps.domain.repository.MovieRepository
import com.example.filmkuapps.domain.usecase.CreditsMoviesUseCase
import com.example.filmkuapps.domain.usecase.DetailMoviesUseCase
import com.example.filmkuapps.domain.usecase.GetNowPlayingMoviesUseCase
import com.example.filmkuapps.domain.usecase.GetPopularMoviesUseCase
import com.example.filmkuapps.domain.usecase.GetTopRatedMoviesUseCase
import com.example.filmkuapps.domain.usecase.GetUpcomingMoviesUseCase
import com.example.filmkuapps.domain.usecase.ReviewMoviesUseCase
import com.example.filmkuapps.domain.usecase.SearchMoviesUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {
    // Setup OkHttp Client dengan Logger
    private val okHttpClient: OkHttpClient by lazy {
        // Buat custom logger untuk merapikan JSON
        val customLogger = HttpLoggingInterceptor.Logger { message ->
            val logName = "API_RESPONSE"
            if (message.startsWith("{") || message.startsWith("[")) {
                try {
                    // Jika teks berupa JSON, format dengan indentasi 4 spasi
                    val prettyPrintJson = if (message.startsWith("{")) {
                        JSONObject(message).toString(4)
                    } else {
                        JSONArray(message).toString(4)
                    }
                    Log.d(logName, prettyPrintJson)
                } catch (e: Exception) {
                    Log.d(logName, message) // Jika gagal parse, cetak normal
                }
            } else {
                // Cetak log non-JSON (seperti header, url, dll)
                Log.d(logName, message)
            }
        }

        // Masukkan customLogger ke dalam Interceptor
        val loggingInterceptor = HttpLoggingInterceptor(customLogger).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    // 1. Setup Retrofit
    private val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    // 2. Setup Repository
    private val movieRepository: MovieRepository by lazy {
        MovieRepositoryImpl(apiService)
    }

    val getPopularMovieUseCase: GetPopularMoviesUseCase by lazy {
        GetPopularMoviesUseCase(movieRepository)
    }
    val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase by lazy {
        GetNowPlayingMoviesUseCase(movieRepository)
    }

    val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase by lazy {
        GetUpcomingMoviesUseCase(movieRepository)
    }

    val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase by lazy {
        GetTopRatedMoviesUseCase(movieRepository)
    }

    val detailMoviesUseCase: DetailMoviesUseCase by lazy {
        DetailMoviesUseCase(repository = movieRepository)
    }

    val reviewMoviesUseCase: ReviewMoviesUseCase by lazy {
        ReviewMoviesUseCase(repository = movieRepository)
    }

    val creditMoviesUseCase: CreditsMoviesUseCase by lazy {
        CreditsMoviesUseCase(repository = movieRepository)
    }

    val searchMoviesUseCase: SearchMoviesUseCase by lazy {
        SearchMoviesUseCase(repository = movieRepository)
    }
}