package com.aruna.kliknelayanwakatobi.httprequest

import android.text.TextUtils
import com.aruna.kliknelayanwakatobi.pojo.ModelBase
import com.aruna.kliknelayanwakatobi.tools.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by marzellamega on 4/26/16.
 */
abstract class MyCallback<T : ModelBase> : Callback<T> {

    override fun onResponse(call: Call<T>?, response: Response<T>?) {
        Utils.Log("success", "response success ");
        if (response?.isSuccessful == true) {
            val body = response.body()
            if (body is ModelBase) {
                if (body.status == "success") {
                    onSuccess(response)
                }
                if (!TextUtils.isEmpty(body.message)) {
                    onMessage(body.message)
                }
            }
        } else {
            onSaveLocaly();
        }
        onFinish()
    }

    override fun onFailure(call: Call<T>?, t: Throwable) {

        Utils.Log("failed", "response failed " + t.message + " ");
        if (!onSaveLocaly()) {
            onMessage(t.message ?: "No Message")
        } else {
            call?.cancel()
        }
        onFinish()
    }

    fun onSaveLocaly(): Boolean {
        //        Utils.Log("local save", "local save");
        return false
    }

    abstract fun onSuccess(response: Response<*>)

    abstract fun onMessage(msg: String)

    abstract fun onFinish()
}