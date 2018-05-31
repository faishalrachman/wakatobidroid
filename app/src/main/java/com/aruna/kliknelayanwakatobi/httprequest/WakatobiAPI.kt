package com.aruna.kliknelayanwakatobi.httprequest

import com.aruna.kliknelayanwakatobi.pojo.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by marzellaalfamega on 12/7/17.
 */
interface WakatobiAPI {

    @POST("auth")
    fun doLogin(@Body loginData: MutableMap<String, String>): Call<ModelLogin>

    @GET("user/me")
    fun getUserData(): Call<ModelUserData>

    //?conditions=full_name.icontains:{query}
    @GET("fisherman-user")
    fun searchAnggota(@Query("conditions") query: String): Call<ModelAnggotaData>

    @GET("commodity")
    fun loadComodity(): Call<ModelComodityData>

    @GET("district")
    fun loadDistrict() : Call<ModelDistrictData>

    @GET("sub_district")
    fun loadSubDistrict() : Call<ModelSubDistrictData>
//    //transaction/fisher/{{fisherman_id}}
//    @GET("transaction/fisher/{fisherman_id}")
//    fun getFishermanData(@Path("fisherman_id") fishermanId: String): Call<ModelBase>
}