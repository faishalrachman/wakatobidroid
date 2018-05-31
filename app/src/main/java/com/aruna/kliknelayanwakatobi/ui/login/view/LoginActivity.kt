package com.aruna.kliknelayanwakatobi.ui.login.view

import ControllerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import com.aruna.kliknelayanwakatobi.R
import com.aruna.kliknelayanwakatobi.tools.API_TOKEN
import com.aruna.kliknelayanwakatobi.tools.SPHelper
import com.aruna.kliknelayanwakatobi.ui.BaseActivity
import com.aruna.kliknelayanwakatobi.ui.login.model.LoginModel
import com.aruna.kliknelayanwakatobi.ui.login.presenter.ILoginPresenter
import com.aruna.kliknelayanwakatobi.ui.login.presenter.LoginPresenter
import com.aruna.kliknelayanwakatobi.ui.mainmenu.view.MainMenuActivity
import com.qasico.aruna.extension.getString
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BaseActivity(), ILoginView {
    private var loadingDialog: ProgressDialog? = null

    override fun showLoading() {
        if (loadingDialog == null) {
            loadingDialog = ControllerDialog.showLoading(this)
        } else {
            loadingDialog?.show()
        }

    }

    val presenter: ILoginPresenter

    init {
        presenter = LoginPresenter(this, LoginModel())
        presenter.setupModelListener()

        SPHelper.getInstance().setPreferences(API_TOKEN, "")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
    }

    private fun initView() {
        cbShowPassword.setOnCheckedChangeListener { _, isChecked ->
            showPassword(isChecked)
        }

        btnLogin.setOnClickListener { doLogin() }
    }

    private fun doLogin() {
        presenter.doLogin()
    }

    override fun gotoMainMenu() {
        dissmissLoading()
        val intent = Intent(this, MainMenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun dissmissLoading() {
        if (loadingDialog != null && loadingDialog?.isShowing == true) {
            loadingDialog?.dismiss()
        }
    }

    override fun showMessage(msg: String) {

    }


    private fun showPassword(checked: Boolean) {
        if (checked) {
            edtPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            edtPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        }
    }

    override fun getUsername(): String {
        return edtUsername.getString()
    }

    override fun getPassword(): String {
        return edtPassword.getString()
    }
}