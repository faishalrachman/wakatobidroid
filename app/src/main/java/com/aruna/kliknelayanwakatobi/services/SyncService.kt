package com.aruna.kliknelayanwakatobi.services

import android.app.IntentService
import android.content.Intent
import com.aruna.kliknelayanwakatobi.httprequest.MyCallback
import com.aruna.kliknelayanwakatobi.httprequest.RetrofitAPI
import com.aruna.kliknelayanwakatobi.pojo.ModelBase
import com.aruna.kliknelayanwakatobi.pojo.ModelToken
import com.aruna.kliknelayanwakatobi.pojo.ModelUploadAllParams
import com.aruna.kliknelayanwakatobi.tools.SERVICE_SYNC
import io.realm.Realm
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Response

/**
 * Created by marzellaalfamega on 26/12/17.
 */
class SyncService : IntentService(SERVICE_SYNC) {

    var data: MutableList<ModelUploadAllParams>? = null
    var myRealm: Realm? = null
    override fun onHandleIntent(p0: Intent?) {
        myRealm = Realm.getDefaultInstance()
        getAllData()
        syncData()
    }

    private fun getAllData() {
        myRealm?.executeTransaction { realm ->
            data = realm.where(ModelUploadAllParams::class.java).findAll()
        }
    }

    private fun syncData() {

        if (data != null) {
            if (data!!.size > 0) {
                RetrofitAPI.wakatobiFishTankApi.getTokenFishTank().enqueue(object : MyCallback<ModelToken>() {
                    override fun onSuccess(response: Response<*>) {
                        val body = response.body()
                        if (body is ModelToken) {
                            val requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), data!![0].allParams)
                            RetrofitAPI.wakatobiFishTankApi.transaction(body.data, requestBody).enqueue(object : MyCallback<ModelBase>() {
                                override fun onSuccess(response: Response<*>) {
                                    data!!.removeAt(0)
                                    if (data!!.size > 0) {
                                        syncData()
                                    }
                                }

                                override fun onMessage(msg: String) {

                                }

                                override fun onFinish() {

                                }

                            })
                        }
                    }

                    override fun onMessage(msg: String) {

                    }

                    override fun onFinish() {

                    }

                })
            } else {
                myRealm?.close()
            }
        } else {
            myRealm?.close()
        }
    }
}