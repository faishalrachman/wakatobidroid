package com.aruna.kliknelayanwakatobi.ui.login.model

import com.aruna.kliknelayanwakatobi.httprequest.MyCallback
import com.aruna.kliknelayanwakatobi.httprequest.RetrofitAPI
import com.aruna.kliknelayanwakatobi.pojo.ModelDistrict
import com.aruna.kliknelayanwakatobi.pojo.ModelDistrictData
import com.aruna.kliknelayanwakatobi.pojo.ModelLogin
import com.aruna.kliknelayanwakatobi.pojo.ModelSubDistrictData
import com.aruna.kliknelayanwakatobi.tools.API_TOKEN
import com.aruna.kliknelayanwakatobi.tools.ENTITY_APP
import com.aruna.kliknelayanwakatobi.tools.SPHelper
import com.aruna.kliknelayanwakatobi.ui.login.presenter.ILoginPresenter
import io.realm.Realm
import retrofit2.Response

/**
 * Created by marzellaalfamega on 12/3/17.
 */
class LoginModel : ILoginModel {
    override fun doLogin(username: String, password: String) {

        val mapLogin = mutableMapOf<String, String>()

        mapLogin.put("password", password)
        mapLogin.put("username", username)
        mapLogin.put("entity_app", ENTITY_APP)

        RetrofitAPI.wakatobiApi.doLogin(mapLogin).enqueue(object : MyCallback<ModelLogin>() {
            override fun onSuccess(response: Response<*>) {
                val body = response.body()
                if (body is ModelLogin) {
                    saveAuth(body.data)
                    val myRealm = Realm.getDefaultInstance()
                    val result = myRealm.where(ModelDistrict::class.java).findAll()
                    if (result != null && result.size > 0) {
                        presenter.gotoMainMenu()
                    } else {
                        getDistrict()
                    }
                }
            }

            override fun onMessage(msg: String) {
                presenter.showMessage(msg)
            }

            override fun onFinish() {

            }

        })
    }

    private fun getDistrict() {
        RetrofitAPI.wakatobiApi.loadDistrict().enqueue(object : MyCallback<ModelDistrictData>() {
            override fun onSuccess(response: Response<*>) {
                val body = response.body()
                if (body != null) {
                    if (body is ModelDistrictData) {
                        val myRealm = Realm.getDefaultInstance()
                        myRealm.executeTransaction { realm ->
                            if (body.data != null) {
                                realm.copyToRealmOrUpdate(body.data!!) //null checking implemented
                            }
                        }
                    }
                }
                getSubDistrict()
            }

            override fun onMessage(msg: String) {

            }

            override fun onFinish() {

            }

        })
    }

    private fun getSubDistrict() {
        RetrofitAPI.wakatobiApi.loadSubDistrict().enqueue(object : MyCallback<ModelSubDistrictData>() {
            override fun onSuccess(response: Response<*>) {
                val body = response.body()
                if (body != null) {
                    if (body is ModelSubDistrictData) {
                        val myRealm = Realm.getDefaultInstance()
                        myRealm.executeTransaction { realm ->
                            if (body.data != null) {
                                realm.copyToRealmOrUpdate(body.data!!) //null checking implemented
                            }
                        }
                        presenter.gotoMainMenu()
                    }
                }
            }

            override fun onMessage(msg: String) {

            }

            override fun onFinish() {

            }

        })
    }

    private fun saveAuth(data: String) {
        SPHelper.getInstance().setPreferences(API_TOKEN, data)
    }

    lateinit var presenter: ILoginPresenter

    override fun setupModelListener(loginPresenter: ILoginPresenter) {
        this.presenter = loginPresenter
    }

}