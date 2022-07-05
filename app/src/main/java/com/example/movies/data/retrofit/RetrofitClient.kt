import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.themoviedb.org/3/"

object RetrofitService {
    val retrofitService: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(OkHttpClient.Builder().apply {
            addInterceptor { chain ->
                Log.i("OKHHTP", chain.request().url.toString())
                val newUrl = chain.request().url.newBuilder().addQueryParameter("api_key", "39fd0a08c0cc7fd3041fc14605c22358").build()
                chain.proceed(chain.request().newBuilder().url(newUrl).build())
            }
        }.build()).build()
}