package com.aruna.kliknelayanwakatobi.ui.produksihasillaut.model

import com.aruna.kliknelayanwakatobi.ui.produksihasillaut.presenter.IProduksiHasilLautPresenter

/**
 * Created by marzellaalfamega on 12/4/17.
 */
interface IProduksiHasilLautModel {
    fun setupModelListener(presenter: IProduksiHasilLautPresenter)
//    fun searchAnggota(query: String)
    fun getFishermanData()
}