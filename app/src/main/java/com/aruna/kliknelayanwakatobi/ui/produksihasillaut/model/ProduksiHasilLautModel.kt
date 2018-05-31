package com.aruna.kliknelayanwakatobi.ui.produksihasillaut.model

import com.aruna.kliknelayanwakatobi.httprequest.MyCallback
import com.aruna.kliknelayanwakatobi.httprequest.RetrofitAPI
import com.aruna.kliknelayanwakatobi.pojo.ModelAnggotaData
import com.aruna.kliknelayanwakatobi.pojo.ModelTransactionData
import com.aruna.kliknelayanwakatobi.tools.USER_DATA
import com.aruna.kliknelayanwakatobi.ui.produksihasillaut.presenter.IProduksiHasilLautPresenter
import retrofit2.Response

/**
 * Created by marzellaalfamega on 12/4/17.
 */
class ProduksiHasilLautModel : IProduksiHasilLautModel {

    override fun getFishermanData() {
        RetrofitAPI.wakatobiFishTankApi.getFishermanData(USER_DATA?.user?.id!!).enqueue(object : MyCallback<ModelTransactionData>() {
            override fun onSuccess(response: Response<*>) {
                val body = response.body()
                if (body is ModelTransactionData) {
                    if (body.data != null) {
                        presenter.successGetDataTransaction(body.data!!)
                    } else {
                        presenter.noDataFound()
                    }
                }
            }

            override fun onMessage(msg: String) {

            }

            override fun onFinish() {

            }

        })
    }

//    override fun searchAnggota(query: String) {
//        val condition = "full_name.icontains:$query"
//        RetrofitAPI.wakatobiApi.searchAnggota(condition).enqueue(object : MyCallback<ModelAnggotaData>() {
//            override fun onSuccess(response: Response<*>) {
//                val body = response.body()
//                if (body != null) {
//                    if (body is ModelAnggotaData) {
//                        presenter.successGetAnggota(body)
//                    }
//                } else {
//                    presenter.clearSuggestion()
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
//    }

    private lateinit var presenter: IProduksiHasilLautPresenter

    override fun setupModelListener(presenter: IProduksiHasilLautPresenter) {
        this.presenter = presenter
    }
}