package com.example.afreecaandroid.data.api

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.afreecaandroid.data.di.AppInterceptor
import com.example.afreecaandroid.data.model.CategoryApiDTO
import com.example.afreecaandroid.data.model.DataSourceDTO
import com.example.afreecaandroid.uitl.Constants
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class ApiTest {

    private lateinit var retrofit: Retrofit
    private lateinit var okHttpClient: OkHttpClient
    @Inject
    val appInterceptor = AppInterceptor()
    private lateinit var categoryApiTest: CategoryApiTest
    private lateinit var talkCamApiTest: TalkCamApiTest
    private lateinit var travelApiTest: TravelApiTest
    private lateinit var cookApiTest: CookApiTest

    @Before
    fun setUp() {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(appInterceptor)
            .build()

        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .build()

        categoryApiTest = retrofit.create(CategoryApiTest::class.java)
        talkCamApiTest = retrofit.create(TalkCamApiTest::class.java)
        travelApiTest = retrofit.create(TravelApiTest::class.java)
        cookApiTest = retrofit.create(CookApiTest::class.java)
    }

    @Test
    fun `카테고리 테스트`() = runTest {
        val response: Response<CategoryApiDTO> = categoryApiTest.getCategory(Constants.CLIENT_ID)
        assertThat(response.isSuccessful).isEqualTo(true)
        assertThat(response.code()).isEqualTo(200)
    }

    @Test
    fun `토크 캠방 테스트`() = runTest {
        val response: Response<DataSourceDTO> =
            talkCamApiTest.searchTalkCamBroadcast(Constants.CLIENT_ID, 1)
        assertThat(response.isSuccessful).isEqualTo(true)
        assertThat(response.code()).isEqualTo(200)
    }

    @Test
    fun `여행 테스트`() = runTest {
        val response: Response<DataSourceDTO> =
            travelApiTest.searchTravelBroadcast(Constants.CLIENT_ID, 1)
        assertThat(response.isSuccessful).isEqualTo(true)
        assertThat(response.code()).isEqualTo(200)
    }

    @Test
    fun `먹방 쿡방 테스트`() = runTest {
        val response: Response<DataSourceDTO> =
            cookApiTest.searchCookBroadcast(Constants.CLIENT_ID, 1)
        assertThat(response.isSuccessful).isEqualTo(true)
        assertThat(response.code()).isEqualTo(200)
    }
}
