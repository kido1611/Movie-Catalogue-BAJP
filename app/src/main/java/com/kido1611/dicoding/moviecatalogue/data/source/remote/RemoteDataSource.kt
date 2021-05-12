package com.kido1611.dicoding.moviecatalogue.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kido1611.dicoding.moviecatalogue.data.source.UIState
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
    fun getDiscoverMovie(): LiveData<UIState<List<Movie>>> {
        EspressoIdlingResource.increment()
        val result = MutableLiveData<UIState<List<Movie>>>()
        result.value = UIState.Loading

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
                            result.value = UIState.Success(it)
                        }
                    } else {
                        result.value = UIState.Error(response.message())
                    }
                }

                override fun onFailure(call: Call<RemoteDiscoverResponse>, t: Throwable) {
                    EspressoIdlingResource.decrement()
                    result.value = UIState.Error(t.message.toString())
                }

            })
        return result
    }

    fun getDiscoverTv(): LiveData<UIState<List<Movie>>> {
        EspressoIdlingResource.increment()
        val result = MutableLiveData<UIState<List<Movie>>>()
        result.value = UIState.Loading

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
                            result.value = UIState.Success(it)
                        }
                    } else {
                        result.value = UIState.Error(response.message())
                    }
                }

                override fun onFailure(call: Call<RemoteDiscoverResponse>, t: Throwable) {
                    EspressoIdlingResource.decrement()
                    result.value = UIState.Error(t.message.toString())
                }

            })
        return result
    }

    fun getMovieById(id: Int): LiveData<UIState<Movie>> {
        EspressoIdlingResource.increment()
        val result = MutableLiveData<UIState<Movie>>()
        result.value = UIState.Loading

        service.getMovieById(id)
            .enqueue(object : Callback<Movie> {
                override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                    EspressoIdlingResource.decrement()
                    if (response.isSuccessful) {
                        response.body()?.let {
                            result.value = UIState.Success(it)
                        }
                    } else {
                        result.value = UIState.Error(response.message())
                    }
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    EspressoIdlingResource.decrement()
                    result.value = UIState.Error(t.message.toString())
                }

            })
        return result
    }

    fun getTvById(id: Int): LiveData<UIState<Movie>> {
        EspressoIdlingResource.increment()
        val result = MutableLiveData<UIState<Movie>>()
        result.value = UIState.Loading

        service.getTvById(id).enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                EspressoIdlingResource.decrement()
                if (response.isSuccessful) {
                    response.body()?.let {
                        result.value = UIState.Success(it)
                    }
                } else {
                    result.value = UIState.Error(response.message())
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                EspressoIdlingResource.decrement()
                result.value = UIState.Error(t.message.toString())
            }

        })
        return result
    }
}