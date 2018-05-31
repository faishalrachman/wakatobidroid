package com.aruna.kliknelayanwakatobi.httprequest

import com.aruna.kliknelayanwakatobi.pojo.ModelBase
import com.aruna.kliknelayanwakatobi.pojo.ModelToken
import com.aruna.kliknelayanwakatobi.pojo.ModelTransactionData
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by marzellaalfamega on 12/7/17.
 */
interface WakatobiFishTankAPI {

    @GET("token")
    fun getTokenFishTank(): Call<ModelToken>

    //transaction/fisher/{{fisherman_id}}
    @GET("transaction/fisher/{fisherman_id}")
    fun getFishermanData(@Path("fisherman_id") fishermanId: String): Call<ModelTransactionData>

    @POST("transaction/{token}")
    fun transaction(@Path("token") token: String, @Body jsonBody: RequestBody): Call<ModelBase>
}