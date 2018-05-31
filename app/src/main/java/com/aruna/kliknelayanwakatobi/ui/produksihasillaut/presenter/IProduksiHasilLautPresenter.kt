package com.aruna.kliknelayanwakatobi.ui.produksihasillaut.presenter

import com.aruna.kliknelayanwakatobi.pojo.ModelAnggotaData
import com.aruna.kliknelayanwakatobi.pojo.ModelTransaction

/**
 * Created by marzellaalfamega on 12/4/17.
 */
interface IProduksiHasilLautPresenter {
    fun setupModelListener()
//    fun searchAnggota()
//    fun successGetAnggota(modelAnggotaData: ModelAnggotaData)
//    fun clearSuggestion()
//    fun getFishermanData(position: Int)
    fun successGetDataTransaction(data: MutableList<ModelTransaction>)
    fun noDataFound()
    fun getEnumeratorData()
}