package com.aruna.kliknelayanwakatobi.ui.inserthasilproduksi.view

import com.aruna.kliknelayanwakatobi.pojo.ModelComodity
import com.aruna.kliknelayanwakatobi.pojo.ModelForm

/**
 * Created by marzellaalfamega on 12/5/17.
 */
interface IInputHasilProduksi {
    fun isFormReady(): Boolean
    fun uploadImage()
    fun getAllData(): MutableList<ModelForm>
    fun finishSaveData()
    fun showLoading()
    fun dissmissLoading()
    fun setAllCommodity(data: MutableList<ModelComodity>?)
    fun setupView()
}