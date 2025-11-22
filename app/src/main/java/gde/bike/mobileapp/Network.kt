package gde.bike.mobileapp

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

public class Network {
    // Retrofit példány, csak első használatkor hozzuk létre
    val retrofit: Retrofit by lazy {
        // Retrofit Builder dokumentáció: https://square.github.io/retrofit/2.x/retrofit/retrofit2/Retrofit.Builder.html
        Retrofit.Builder()
            // innen húzzuk le a JSON-t
            .baseUrl("https://raw.githubusercontent.com/udemx/hr-resources/refs/heads/master/")
            // HTTP kliens + interceptor, ami kiírja a hálózati forgalmat a log-ba
            .client(OkHttpClient.Builder()
                // OKHTTP logging interceptor dokumentáció: https://square.github.io/okhttp/3.x/logging-interceptor/okhttp3/logging/HttpLoggingInterceptor.html
                .addInterceptor(HttpLoggingInterceptor().apply {
                    // 'Body szintű' logolás
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }).build()
            )
            // JSON-ból Kotlin object konvertálás Gson-nal
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    // Retrofit által létrehozott API interface
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
interface ApiService {
    // GET request a bicycles.json fájlra, ebből lista 'bike' objectekből
    @GET("bicycles.json")
    fun loadBicycles(): Call<List<Bike>>
}