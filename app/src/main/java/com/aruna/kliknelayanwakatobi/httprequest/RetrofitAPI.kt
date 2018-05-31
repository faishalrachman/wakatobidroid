package com.aruna.kliknelayanwakatobi.httprequest

import android.text.TextUtils
import com.aruna.kliknelayanwakatobi.BuildConfig
import com.aruna.kliknelayanwakatobi.tools.API_TOKEN
import com.aruna.kliknelayanwakatobi.tools.SPHelper
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


/**
 * Created by marzellaalfamega on 12/7/17.
 */
object RetrofitAPI {

    val wakatobiApi: WakatobiAPI
    val wakatobiFishTankApi: WakatobiFishTankAPI

    init {
        val logging = HttpLoggingInterceptor()
// set your desired log level
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient
                .addInterceptor(logging)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(object : Interceptor {
                    @Throws(IOException::class)
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val newRequest = chain.request().newBuilder()
                        val token = SPHelper.getInstance().getSharedPreferences(API_TOKEN, "")
                        if (!TextUtils.isEmpty(token)) {
                            newRequest.addHeader("Authorization", "Bearer " + token)
                        }
                        return chain.proceed(newRequest.build())
                    }
                })


        val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.URL_BASE)
                .client(httpClient.build())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        wakatobiApi = retrofit.create(WakatobiAPI::class.java)

        val retrofitFishTank = Retrofit.Builder()
                .baseUrl(BuildConfig.URL_BASE_FISH_TANK)
                .client(httpClient.build())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        wakatobiFishTankApi = retrofitFishTank.create(WakatobiFishTankAPI::class.java)
    }

}