package com.aruna.kliknelayanwakatobi.ui.produksihasillaut.view

import com.aruna.kliknelayanwakatobi.pojo.ModelAnggota
import com.aruna.kliknelayanwakatobi.pojo.ModelTransaction

/**
 * Created by marzellaalfamega on 12/4/17.
 */
interface IProduksiHasilLautView {
    fun getQuery(): String
//    fun updateSuggestion(listModelAnggota: MutableList<ModelAnggota>)
//    fun clearSuggestion()
    fun getFishermanId(position: Int): String
    fun successGetDataTransaction(data: MutableList<ModelTransaction>)
}