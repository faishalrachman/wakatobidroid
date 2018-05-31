package com.aruna.kliknelayanwakatobi.ui.produksihasillaut.presenter

import com.aruna.kliknelayanwakatobi.pojo.ModelAnggotaData
import com.aruna.kliknelayanwakatobi.pojo.ModelTransaction
import com.aruna.kliknelayanwakatobi.ui.produksihasillaut.model.IProduksiHasilLautModel
import com.aruna.kliknelayanwakatobi.ui.produksihasillaut.view.IProduksiHasilLautView

/**
 * Created by marzellaalfamega on 12/4/17.
 */
class ProduksiHasilLautPresenter(val view: IProduksiHasilLautView, val model: IProduksiHasilLautModel) : IProduksiHasilLautPresenter {
    override fun getEnumeratorData() {
        model.getFishermanData()
    }

    override fun noDataFound() {

    }

    override fun successGetDataTransaction(data: MutableList<ModelTransaction>) {
        view.successGetDataTransaction(data)
    }

//    override fun getFishermanData(position: Int) {
//        model.getFishermanData(view.getFishermanId(position))
//    }

//    override fun clearSuggestion() {
//        view.clearSuggestion()
//    }

//    override fun successGetAnggota(modelAnggotaData: ModelAnggotaData) {
//        if (modelAnggotaData.data != null) {
//            view.updateSuggestion(modelAnggotaData.data!!)
//        } else {
//            view.clearSuggestion()
//        }
//    }

//    override fun searchAnggota() {
//        model.searchAnggota(view.getQuery())
//    }

    override fun setupModelListener() {
        model.setupModelListener(this)
    }
}