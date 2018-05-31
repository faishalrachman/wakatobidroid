package com.aruna.kliknelayanwakatobi.ui.inserthasilproduksi.model

import com.aruna.kliknelayanwakatobi.pojo.ModelForm
import com.aruna.kliknelayanwakatobi.ui.inserthasilproduksi.presenter.IInputHasilProduksiPresenter

/**
 * Created by marzellaalfamega on 12/5/17.
 */
interface IInputHasilProduksiModel {
    fun setupModelListener(presenter: IInputHasilProduksiPresenter)
    fun saveDataProduct(allData: MutableList<ModelForm>)
    fun loadComodity()
    fun loadDistrict()
}