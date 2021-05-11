package com.kido1611.dicoding.moviecatalogue.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kido1611.dicoding.moviecatalogue.data.source.DetailUiState
import com.kido1611.dicoding.moviecatalogue.data.source.DiscoverUiState
import com.kido1611.dicoding.moviecatalogue.model.Movie
import com.kido1611.dicoding.moviecatalogue.model.RemoteDiscoverResponse
import com.kido1611.dicoding.moviecatalogue.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val service: TMDBService
) {
    fun getDiscoverMovie(): LiveData<DiscoverUiState> {
        EspressoIdlingResource.increment()
        val result = MutableLiveData<DiscoverUiState>()
        result.value = DiscoverUiState.Loading(null)

        service.getDiscoverMovie(1)
            .enqueue(object : Callback<RemoteDiscoverResponse> {
                override fun onResponse(
                    call: Call<RemoteDiscoverResponse>,
                    response: Response<RemoteDiscoverResponse>
                ) {
                    EspressoIdlingResource.decrement()
                    if (response.isSuccessful) {
                        val data = response.body()?.results
                        data?.let {
                            result.value = DiscoverUiState.Success(it)
                        }
                    } else {
                        result.value = DiscoverUiState.Error(response.message())
                    }
                }

                override fun onFailure(call: Call<RemoteDiscoverResponse>, t: Throwable) {
                    EspressoIdlingResource.decrement()
                    result.value = DiscoverUiState.Error(t.message.toString())
                }

            })
        return result
    }

    fun getDiscoverTv(): LiveData<DiscoverUiState> {
        EspressoIdlingResource.increment()
        val result = MutableLiveData<DiscoverUiState>()
        result.value = DiscoverUiState.Loading(null)

        service.getDiscoverTv(1)
            .enqueue(object : Callback<RemoteDiscoverResponse> {
                override fun onResponse(
                    call: Call<RemoteDiscoverResponse>,
                    response: Response<RemoteDiscoverResponse>
                ) {
                    EspressoIdlingResource.decrement()
                    if (response.isSuccessful) {
                        val data = response.body()?.results
                        data?.let {
                            result.value = DiscoverUiState.Success(it)
                        }
                    } else {
                        result.value = DiscoverUiState.Error(response.message())
                    }
                }

                override fun onFailure(call: Call<RemoteDiscoverResponse>, t: Throwable) {
                    EspressoIdlingResource.decrement()
                    result.value = DiscoverUiState.Error(t.message.toString())
                }

            })
        return result
    }

    fun getMovieById(id: Int): LiveData<DetailUiState> {
        EspressoIdlingResource.increment()
        val result = MutableLiveData<DetailUiState>()
        result.value = DetailUiState.Loading(null)

        service.getMovieById(id)
            .enqueue(object : Callback<Movie> {
                override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                    EspressoIdlingResource.decrement()
                    if (response.isSuccessful) {
                        response.body()?.let {
                            result.value = DetailUiState.Success(it)
                        }
                    } else {
                        result.value = DetailUiState.Error(response.message())
                    }
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    EspressoIdlingResource.decrement()
                    result.value = DetailUiState.Error(t.message.toString())
                }

            })
        return result
    }

    fun getTvById(id: Int): LiveData<DetailUiState> {
        EspressoIdlingResource.increment()
        val result = MutableLiveData<DetailUiState>()
        result.value = DetailUiState.Loading(null)

        service.getTvById(id).enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                EspressoIdlingResource.decrement()
                if (response.isSuccessful) {
                    response.body()?.let {
                        result.value = DetailUiState.Success(it)
                    }
                } else {
                    result.value = DetailUiState.Error(response.message())
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                EspressoIdlingResource.decrement()
                result.value = DetailUiState.Error(t.message.toString())
            }

        })
        return result
    }
}