package com.aruna.kliknelayanwakatobi.ui.mainmenu.view

import android.content.Intent
import android.os.Bundle
import com.aruna.kliknelayanwakatobi.R
import com.aruna.kliknelayanwakatobi.pojo.ModelUser
import com.aruna.kliknelayanwakatobi.pojo.ModelUserData
import com.aruna.kliknelayanwakatobi.services.SyncService
import com.aruna.kliknelayanwakatobi.tools.EXTRA_MODEL_USER
import com.aruna.kliknelayanwakatobi.tools.Utils
import com.aruna.kliknelayanwakatobi.ui.BaseActivity
import com.aruna.kliknelayanwakatobi.ui.mainmenu.model.MainMenuModel
import com.aruna.kliknelayanwakatobi.ui.mainmenu.presenter.IMainMenuPresenter
import com.aruna.kliknelayanwakatobi.ui.mainmenu.presenter.MainMenuPresenter
import com.aruna.kliknelayanwakatobi.ui.produksihasillaut.view.ProduksiHasilLautActivity
import kotlinx.android.synthetic.main.activity_main_menu.*
import java.util.ArrayList

class MainMenuActivity : BaseActivity(), IMainMenuActivity {

    val presenter: IMainMenuPresenter
    var modelUser: ModelUser? = null

    init {
        presenter = MainMenuPresenter(this, MainMenuModel())
        presenter.setupModelListener()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        initView()

        presenter.loadUser()
    }

    override fun initDataUser(modelUserData: ModelUserData) {
        modelUser = modelUserData.data?.user
//        Utils.Log("MainMenuActivity","set user data ${body.data.id}")
        tvNameMainMenu.text = modelUserData.data?.user?.full_name
        tvDepartmentMainMenu.text = modelUserData.data?.user?.user_group?.user_group_name
        tvLocationMainMenu.text = modelUserData.data?.user?.address
    }

    private fun initView() {

        btnProduksiHasilLautMainMenu.setOnClickListener { gotoProduksiHasilLaut() }

        btnChatBantuanMainMenu.setOnClickListener { gotoChat() }
    }

    private fun gotoChat() {

    }

    private fun gotoProduksiHasilLaut() {
        val intent = Intent(this, ProduksiHasilLautActivity::class.java)
        intent.putExtra(EXTRA_MODEL_USER, modelUser)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        Utils.startSync(this)
    }

}