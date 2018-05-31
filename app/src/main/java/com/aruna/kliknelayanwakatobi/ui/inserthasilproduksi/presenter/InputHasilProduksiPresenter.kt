package com.aruna.kliknelayanwakatobi.ui.inserthasilproduksi.presenter

import com.aruna.kliknelayanwakatobi.pojo.ModelComodity
import com.aruna.kliknelayanwakatobi.tools.Utils
import com.aruna.kliknelayanwakatobi.ui.inserthasilproduksi.model.IInputHasilProduksiModel
import com.aruna.kliknelayanwakatobi.ui.inserthasilproduksi.view.IInputHasilProduksi

/**
 * Created by marzellaalfamega on 12/5/17.
 */
class InputHasilProduksiPresenter(val view: IInputHasilProduksi, val model: IInputHasilProduksiModel) : IInputHasilProduksiPresenter {
    override fun loadDistrict() {
        model.loadDistrict()
    }

    override fun successGetCommodityData(data: MutableList<ModelComodity>?) {
        view.dissmissLoading()
        view.setAllCommodity(data)
        view.setupView()
    }

    override fun loadComodity() {
        view.showLoading()
        model.loadComodity()
    }

    override fun finishSaveData() {
        view.dissmissLoading()
        view.finishSaveData()
    }

    override fun saveDataProduct() {
        model.saveDataProduct(view.getAllData())
    }

    override fun saveProduct() {
        if (view.isFormReady()) {
            Utils.Log("InputHasilProduksiPresenter", "form ready")
            view.showLoading()
            view.uploadImage()
        }
    }

    override fun setupModelListener() {
        model.setupModelListener(this)
    }
}