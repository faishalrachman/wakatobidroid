package com.aruna.kliknelayanwakatobi.ui.mainmenu.presenter

import com.aruna.kliknelayanwakatobi.pojo.ModelUserData

/**
 * Created by marzellaalfamega on 12/8/17.
 */
interface IMainMenuPresenter {
    fun setupModelListener()
    fun loadUser()
    fun initDataUser(modelUserData: ModelUserData)
}