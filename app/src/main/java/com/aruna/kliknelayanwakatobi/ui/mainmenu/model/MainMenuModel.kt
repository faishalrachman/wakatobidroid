package com.aruna.kliknelayanwakatobi.ui.mainmenu.model

import com.aruna.kliknelayanwakatobi.httprequest.MyCallback
import com.aruna.kliknelayanwakatobi.httprequest.RetrofitAPI
import com.aruna.kliknelayanwakatobi.pojo.ModelUserData
import com.aruna.kliknelayanwakatobi.tools.USER_DATA
import com.aruna.kliknelayanwakatobi.ui.mainmenu.presenter.IMainMenuPresenter
import retrofit2.Response

/**
 * Created by marzellaalfamega on 12/8/17.
 */
class MainMenuModel : IMainMenuModel {
    override fun loadUser() {
        RetrofitAPI.wakatobiApi.getUserData().enqueue(object : MyCallback<ModelUserData>() {
            override fun onSuccess(response: Response<*>) {
                val body = response.body()
                if (body != null && body is ModelUserData) {
                    USER_DATA = body.data
                    presenter.initDataUser(body)
                }
            }

            override fun onMessage(msg: String) {

            }

            override fun onFinish() {

            }

        })
    }

    lateinit var presenter: IMainMenuPresenter

    override fun setupModelListener(presenter: IMainMenuPresenter) {
        this.presenter = presenter
    }
}