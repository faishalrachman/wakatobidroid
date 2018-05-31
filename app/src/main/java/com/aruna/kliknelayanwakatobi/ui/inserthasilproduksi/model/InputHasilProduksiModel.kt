package com.aruna.kliknelayanwakatobi.ui.inserthasilproduksi.model

import android.text.TextUtils
import com.aruna.kliknelayanwakatobi.customui.form.FormSimpleSuggestion
import com.aruna.kliknelayanwakatobi.httprequest.MyCallback
import com.aruna.kliknelayanwakatobi.httprequest.RetrofitAPI
import com.aruna.kliknelayanwakatobi.pojo.ModelComodityData
import com.aruna.kliknelayanwakatobi.pojo.ModelForm
import com.aruna.kliknelayanwakatobi.pojo.ModelUploadAllParams
import com.aruna.kliknelayanwakatobi.tools.FISHERMAN_DATA
import com.aruna.kliknelayanwakatobi.tools.USER_DATA
import com.aruna.kliknelayanwakatobi.tools.Utils
import com.aruna.kliknelayanwakatobi.ui.inserthasilproduksi.presenter.IInputHasilProduksiPresenter
import io.realm.Realm
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response

/**
 * Created by marzellaalfamega on 12/5/17.
 */
class InputHasilProduksiModel : IInputHasilProduksiModel {
    override fun loadDistrict() {
        RetrofitAPI.wakatobiApi.loadDistrict()
    }

    override fun loadComodity() {
        RetrofitAPI.wakatobiApi.loadComodity().enqueue(object : MyCallback<ModelComodityData>() {
            override fun onSuccess(response: Response<*>) {
                val body = response.body()
                if (body is ModelComodityData) {
                    presenter.successGetCommodityData(body.data)
                }
            }

            override fun onMessage(msg: String) {

            }

            override fun onFinish() {

            }

        })
    }

    override fun saveDataProduct(allData: MutableList<ModelForm>) {
        val fisherData = JSONObject()
        if (USER_DATA != null) {
            fisherData.put("fisher_id", USER_DATA?.user?.id)
            fisherData.put("name", USER_DATA?.user?.full_name)
            fisherData.put("phone", USER_DATA?.user?.phone)
            fisherData.put("email", USER_DATA?.user?.email)
            fisherData.put("photo", USER_DATA?.user?.avatar)

        }

        val fisherMemberData = JSONObject()
        if (FISHERMAN_DATA != null) {
            fisherMemberData.put("fisherman_id", FISHERMAN_DATA?.id)
            fisherMemberData.put("fisherman_type", FISHERMAN_DATA?.fisherman_type)
            fisherMemberData.put("full_name", FISHERMAN_DATA?.full_name)
            fisherMemberData.put("no_ktp", FISHERMAN_DATA?.no_ktp)
            fisherMemberData.put("no_fisherman_card", FISHERMAN_DATA?.no_fisherman_card)
            fisherMemberData.put("no_sipi", FISHERMAN_DATA?.no_sipi)
            fisherMemberData.put("no_siup", FISHERMAN_DATA?.no_siup)
            fisherMemberData.put("no_sikpi", FISHERMAN_DATA?.no_sk)
            fisherMemberData.put("no_insurance", FISHERMAN_DATA?.no_insurance)
            fisherMemberData.put("production_type", FISHERMAN_DATA?.production_type)
        }
        val fishTankMember = JSONObject()
        fishTankMember.put("fisher_data", fisherData)
        fishTankMember.put("fisher_member_data", fisherMemberData)
        allData.forEach { modelForm ->
            if (modelForm.view != null) {
                if (modelForm.view is FormSimpleSuggestion) {
                    val value = modelForm.getValue()
                    value.keys().forEach { key ->
                        fishTankMember.put(key, value[key])
                    }
                } else if (!TextUtils.isEmpty(modelForm.paramKey)) {
                    fishTankMember.put(modelForm.paramKey, modelForm.view?.getValue())
                }
            }
        }
        fishTankMember.put("entity_id", USER_DATA?.entity?.id)
        fishTankMember.put("entity", USER_DATA?.entity?.entity_name)
        fishTankMember.put("production_type", FISHERMAN_DATA?.fisherman_type)
        val fishTank = JSONArray()
        fishTank.put(fishTankMember)

        val parentObject = JSONObject()
        parentObject.put("fish_tank", fishTank)
        val myRealm = Realm.getDefaultInstance()



        myRealm.executeTransaction { realm ->
            val allParams = ModelUploadAllParams()
            allParams.allParams = parentObject.toString()

            realm.copyToRealm(allParams)

        }

        myRealm.close()

        presenter.finishSaveData()
//        val
//        RetrofitAPI.wakatobiFishTankApi.getTokenFishTank().enqueue(object : MyCallback<ModelToken>() {
//            override fun onSuccess(response: Response<*>) {
//                val body = response.body()
//                if (body is ModelToken) {
//                    val requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), parentObject.toString())
//                    RetrofitAPI.wakatobiFishTankApi.transaction(body.data, requestBody).enqueue(object : MyCallback<ModelBase>() {
//                        override fun onSuccess(response: Response<*>) {
//                            presenter.finishSaveData()
//                        }
//
//                        override fun onMessage(msg: String) {
//
//                        }
//
//                        override fun onFinish() {
//
//                        }
//
//                    })
//                }
//            }
//
//            override fun onMessage(msg: String) {
//
//            }
//
//            override fun onFinish() {
//
//            }
//
//        })

        Utils.Log("all fishtank data", parentObject.toString())
    }

    private lateinit var presenter: IInputHasilProduksiPresenter

    override fun setupModelListener(presenter: IInputHasilProduksiPresenter) {
        this.presenter = presenter
    }
}