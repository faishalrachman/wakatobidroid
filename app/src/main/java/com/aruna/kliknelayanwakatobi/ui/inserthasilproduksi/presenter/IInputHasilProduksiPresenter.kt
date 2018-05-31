package com.aruna.kliknelayanwakatobi.ui.inserthasilproduksi.presenter

import com.aruna.kliknelayanwakatobi.pojo.ModelComodity

/**
 * Created by marzellaalfamega on 12/5/17.
 */
interface IInputHasilProduksiPresenter {
    fun setupModelListener()
    fun saveProduct()
    fun saveDataProduct()
    fun finishSaveData()
    fun loadComodity()
    fun successGetCommodityData(data: MutableList<ModelComodity>?)
    fun loadDistrict()
}