package com.kido1611.dicoding.moviecatalogue.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kido1611.dicoding.moviecatalogue.data.source.remote.entity.MovieResponse
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

    fun getMovieById(id: Int): LiveData<ApiResponse<MovieResponse>> {
        EspressoIdlingResource.increment()
        val result = MutableLiveData<ApiResponse<MovieResponse>>()

        service.getMovieById(id)
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    EspressoIdlingResource.decrement()
                    if (response.isSuccessful) {
                        response.body()?.let {
                            result.value = ApiResponse.Success(it)
                        }
                    } else {
                        result.value = ApiResponse.Error(response.message())
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    result.value = ApiResponse.Error(t.message.toString())
                    EspressoIdlingResource.decrement()
                }
            })
        return result
    }

    fun getTvById(id: Int): LiveData<ApiResponse<MovieResponse>> {
        EspressoIdlingResource.increment()
        val result = MutableLiveData<ApiResponse<MovieResponse>>()

        service.getTvById(id).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                EspressoIdlingResource.decrement()
                if (response.isSuccessful) {
                    response.body()?.let {
                        result.value = ApiResponse.Success(it)
                    }
                } else {
                    result.value = ApiResponse.Error(response.message())
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                EspressoIdlingResource.decrement()
                result.value = ApiResponse.Error(t.message.toString())
            }

        })
        return result
    }
}