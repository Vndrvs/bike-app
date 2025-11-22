package gde.bike.mobileapp

import androidx.room.Database
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase

public class Network {
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/udemx/hr-resources/refs/heads/master/")
            .client(OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }).build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
interface ApiService {
    @GET("bicycles.json")
    fun loadBicycles(): Call<List<Bike>>
}