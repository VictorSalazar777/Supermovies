package com.manuelsoft.movies2.network

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.manuelsoft.movies2.R
import com.manuelsoft.movies2.data.Movie
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class RetrofitConnectionTest {

    private lateinit var appContext: Context
    private lateinit var countDownLatch: CountDownLatch
    private lateinit var movieService: FakeMovieService
    private lateinit var call: Call<Movie>

    @Before
    fun init() {
        // Context of the app under test.
        appContext = ApplicationProvider.getApplicationContext()
        countDownLatch = CountDownLatch(1)
        movieService = Retrofit.Builder()
            .baseUrl(appContext.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FakeMovieService::class.java)

        call = movieService.getMovie(appContext.getString(R.string.themoviedb_api_key))
    }

    @Test
    fun retrofitGetsSuccessfulResponseIfResourceExists() {
        var fail = 0
        var isSuccessful = false
        call.enqueue(object: Callback<Movie> {
            override fun onFailure(call: Call<Movie>, t: Throwable) {
                fail = 1
            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                isSuccessful = response.isSuccessful
                println(response.code())
                countDownLatch.countDown()
            }
        })

        countDownLatch.await(2000, TimeUnit.MILLISECONDS)
        assertThat(fail, `is`(0))
        assertThat(isSuccessful, `is`(true))
    }

    @Test
    fun retrofitGetsTheExpectedMovieIfResourceExists() {
        //val inputStream = javaClass.classLoader?.getResourceAsStream("data1.json")
//        val gson = GsonBuilder()
//            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
//            .create()
       // val reader = JsonReader(InputStreamReader(inputStream, "UTF-8"))
      //  val localMovie = gson.fromJson<Movie>(reader, Movie::class.java)
        var remoteMovie: Movie? = null

        call.enqueue(object: Callback<Movie> {
            override fun onFailure(call: Call<Movie>, t: Throwable) {
            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                println(response.code())
                if (response.isSuccessful) {
                    remoteMovie = response.body()
                }
                countDownLatch.countDown()
            }
        })

        countDownLatch.await(2000, TimeUnit.MILLISECONDS)

        assertThat(550, allOf(notNullValue(), equalTo(remoteMovie?.id)))

    }

    @Test
    fun retrofitGetsUnsuccessfulResponseIfResourceDoesNotExist() {
        val call = movieService.getNothing(appContext.getString(R.string.themoviedb_api_key))
        var isSuccessful: Boolean? = null

        call.enqueue(object: Callback<Movie> {
            override fun onFailure(call: Call<Movie>, t: Throwable) {
            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                println(response.code())
                isSuccessful = response.isSuccessful
                countDownLatch.countDown()
            }
        })

        countDownLatch.await(2000, TimeUnit.MILLISECONDS)

        assertThat(isSuccessful, allOf(notNullValue(), equalTo(false)))
    }

    @Test
    fun retrofitGetsFailureIfWebDoesNotExist() {
        val movieService = Retrofit.Builder()
            .baseUrl("https://deep-web")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FakeMovieService::class.java)
        val call = movieService.getMovie(appContext.getString(R.string.themoviedb_api_key))

        var throwable: Throwable? = null

        call.enqueue(object: Callback<Movie> {
            override fun onFailure(call: Call<Movie>, t: Throwable) {
                throwable = t
                println(t.message)
                countDownLatch.countDown()

            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {

            }
        })

        countDownLatch.await(2000, TimeUnit.MILLISECONDS)

        assertThat(throwable, notNullValue())

    }


}
