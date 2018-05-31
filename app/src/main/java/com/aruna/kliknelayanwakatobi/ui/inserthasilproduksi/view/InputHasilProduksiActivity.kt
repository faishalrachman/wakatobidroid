package com.aruna.kliknelayanwakatobi.ui.inserthasilproduksi.view

import ControllerDialog
import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.view.View
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.aruna.kliknelayanwakatobi.R
import com.aruna.kliknelayanwakatobi.customenum.EnumFormType
import com.aruna.kliknelayanwakatobi.customui.form.*
import com.aruna.kliknelayanwakatobi.pojo.ModelComodity
import com.aruna.kliknelayanwakatobi.pojo.ModelDistrict
import com.aruna.kliknelayanwakatobi.pojo.ModelForm
import com.aruna.kliknelayanwakatobi.pojo.ModelSubDistrict
import com.aruna.kliknelayanwakatobi.tools.*
import com.aruna.kliknelayanwakatobi.ui.BaseActivityWithUp
import com.aruna.kliknelayanwakatobi.ui.inserthasilproduksi.model.InputHasilProduksiModel
import com.aruna.kliknelayanwakatobi.ui.inserthasilproduksi.presenter.IInputHasilProduksiPresenter
import com.aruna.kliknelayanwakatobi.ui.inserthasilproduksi.presenter.InputHasilProduksiPresenter
import com.fastaccess.permission.base.PermissionHelper
import com.fastaccess.permission.base.callback.OnPermissionCallback
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_input_hasil_produksi.*
import kotlinx.android.synthetic.main.default_toolbar.*
import java.lang.Exception


class InputHasilProduksiActivity : BaseActivityWithUp(), IInputHasilProduksi, OnPermissionCallback {

    private val MULTI_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    )

    private val listForm = mutableListOf<ModelForm>()
    //    private val listFormView = mutableListOf<View>()
    private lateinit var permissionHelper: PermissionHelper

    private var FORM_TYPE = ""

    private val presenter: IInputHasilProduksiPresenter

    private lateinit var loadingDialog: ProgressDialog

    private var listCommodity = mutableListOf<Any>()
    //District
    private var listKecamatan = mutableListOf<Any>()
    //Sub District
    private var listDesa = mutableListOf<Any>()

    private val myRealm: Realm

    init {
        presenter = InputHasilProduksiPresenter(this, InputHasilProduksiModel())
        presenter.setupModelListener()

        myRealm = Realm.getDefaultInstance()

        getKecamatanLocal()

        getDesaLocal()
    }

    private fun getDesaLocal() {
        val result = myRealm.where(ModelSubDistrict::class.java).findAll()
        if (result != null) {
            listDesa.addAll(myRealm.copyFromRealm(result))
        }
    }

    private fun getKecamatanLocal() {
        val result = myRealm.where(ModelDistrict::class.java).findAll()
        if (result != null) {
            listKecamatan.addAll(myRealm.copyFromRealm(result))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_hasil_produksi)

        permissionHelper = PermissionHelper.getInstance(this)
        permissionHelper.setForceAccepting(true)
        //initview after getting all commodity
        loadingDialog = ControllerDialog.showLoadingNotCancelable(this)
        presenter.loadComodity()
    }

    private fun initView() {
        setupToolbar(myToolbar)

        if (intent.extras.containsKey(EXTRA_FORM_TYPE)) {
            FORM_TYPE = intent.extras.getString(EXTRA_FORM_TYPE)
        }

        when (FORM_TYPE) {
            UPI -> {
                listForm.add(ModelForm("", "Nama Anggota UPI", EnumFormType.SIMPLE_WITH_SUGGESTION, threshold = 1))
                listForm.add(ModelForm("photo", "Foto Produk", EnumFormType.UPLOAD))
                listForm.add(ModelForm("production_date", "Tanggal Produksi", inputType = InputType.TYPE_CLASS_DATETIME))
                listForm.add(ModelForm("quantity", "Kuantitas", EnumFormType.SIMPLE_WITH_UNIT, "Kg"))
                listForm.add(ModelForm("price", "Harga", EnumFormType.SIMPLE_WITH_UNIT, "Rupiah"))
                listForm.add(ModelForm("", "Lokasi Produksi", EnumFormType.TITLE))
                listForm.add(ModelForm("district", "Kecamatan", EnumFormType.SIMPLE_WITH_SUGGESTION,
                        listSuggestion = listKecamatan, threshold = 3))
                listForm.add(ModelForm("village", "Desa / Kelurahan", EnumFormType.SIMPLE_WITH_SUGGESTION, listSuggestion = listDesa
                        , threshold = 3))
            }
            TPI -> {
                listForm.add(ModelForm("", "Nama Anggota TPI", EnumFormType.SIMPLE_WITH_SUGGESTION, threshold = 1))
                listForm.add(ModelForm("", "Komoditas", EnumFormType.SIMPLE_WITH_SUGGESTION, threshold = 1, listSuggestion = listCommodity))
                listForm.add(ModelForm("photo", "Foto Komoditas", EnumFormType.UPLOAD))
                listForm.add(ModelForm("pickup_tools", "Alat Tangkap"))
                listForm.add(ModelForm("quantity", "Kuantitas", EnumFormType.SIMPLE_WITH_UNIT, "Kg"))
                listForm.add(ModelForm("price", "Harga", EnumFormType.SIMPLE_WITH_UNIT, "Rupiah / KG"))
                listForm.add(ModelForm("date_pickup", "Tanggal Penangkapan", inputType = InputType.TYPE_CLASS_DATETIME))
                listForm.add(ModelForm("pickup_geoloc", "Lokasi Penangkapan", EnumFormType.MAP, fragmentManager = supportFragmentManager))
                listForm.add(ModelForm("date_land", "Tanggal Pendaratan", inputType = InputType.TYPE_CLASS_DATETIME))
            }
            TANGKAP -> {
                listForm.add(ModelForm("", "Nama Nelayan", EnumFormType.SIMPLE_WITH_SUGGESTION, threshold = 1))
                listForm.add(ModelForm("", "Komoditas", EnumFormType.SIMPLE_WITH_SUGGESTION, threshold = 1, listSuggestion = listCommodity))
                listForm.add(ModelForm("photo", "Foto Komoditas", EnumFormType.UPLOAD))
                listForm.add(ModelForm("quantity", "Kuantitas", EnumFormType.SIMPLE_WITH_UNIT, "Kg"))
                listForm.add(ModelForm("price", "Harga", EnumFormType.SIMPLE_WITH_UNIT, "Rupiah / KG"))
                listForm.add(ModelForm("pickup_tools", "Alat Tangkap"))
                listForm.add(ModelForm("date_pickup", "Tanggal Penangkapan", inputType = InputType.TYPE_CLASS_DATETIME))
                listForm.add(ModelForm("pickup_geoloc", "Lokasi Penangkapan", EnumFormType.MAP, fragmentManager = supportFragmentManager))
                listForm.add(ModelForm("date_land", "Tanggal Pendaratan", inputType = InputType.TYPE_CLASS_DATETIME))
                listForm.add(ModelForm("", "Lokasi Pendaratan", EnumFormType.TITLE))
                listForm.add(ModelForm("district", "Kecamatan", EnumFormType.SIMPLE_WITH_SUGGESTION, threshold = 4, listSuggestion = listKecamatan))
                listForm.add(ModelForm("village", "Desa / Kelurahan", EnumFormType.SIMPLE_WITH_SUGGESTION, threshold = 4, listSuggestion = listDesa))
            }
            BUDIDAYA -> {
                listForm.add(ModelForm("", "Nama Pembudidaya", EnumFormType.SIMPLE_WITH_SUGGESTION))
                listForm.add(ModelForm("", "Komoditas", EnumFormType.SIMPLE_WITH_SUGGESTION, listSuggestion = listCommodity))
                listForm.add(ModelForm("photo", "Foto Komoditas", EnumFormType.UPLOAD))
                listForm.add(ModelForm("quantity", "Kuantitas", EnumFormType.SIMPLE_WITH_UNIT, "Kg"))
                listForm.add(ModelForm("price", "Harga", EnumFormType.SIMPLE_WITH_UNIT, "Rupiah / KG"))
                listForm.add(ModelForm("", "Lokasi Pembudidaya", EnumFormType.TITLE))
                listForm.add(ModelForm("district", "Kecamatan", EnumFormType.SIMPLE_WITH_SUGGESTION, threshold = 4, listSuggestion = listKecamatan))
                listForm.add(ModelForm("village", "Desa / Kelurahan", EnumFormType.SIMPLE_WITH_SUGGESTION, threshold = 4, listSuggestion = listDesa))
            }
        }

        listForm.add(ModelForm("", "Simpan", EnumFormType.BUTTON, listener = View.OnClickListener { onSaveProduct() }))

        initForm()

    }

    private fun onSaveProduct() {
        presenter.saveProduct()
    }

    private fun initForm() {
        listForm.forEach { model ->
            val view = inflateFormView(model)
            llParentInputHasilProduksi.addView(view)
            model.view = view
        }
    }

    private fun inflateFormView(model: ModelForm): FormBase {
        return when (model.formType) {
            EnumFormType.UPLOAD -> FormUpload(this, model.title)
            EnumFormType.SIMPLE -> FormSimple(this, model.title, model.inputType)
            EnumFormType.SIMPLE_WITH_UNIT -> FormSimpleWitnUnit(this, model.title, model.satuan)
            EnumFormType.TITLE -> FormTitle(this, model.title)
            EnumFormType.BUTTON -> FormButton(this, model.title, model.listener)
            EnumFormType.MAP -> FormMap(this, model.title, model.fragmentManager)
            EnumFormType.SIMPLE_WITH_SUGGESTION -> FormSimpleSuggestion(this, model.title, model.listSuggestion ?: mutableListOf(),
                    model.threshold)
        }
    }

    override fun isFormReady(): Boolean {
        listForm.forEach { form ->
            val view = form.view
            if (view is FormBase) {
                if (!view.isSkipCheck()) {
                    if (TextUtils.isEmpty(view.getValue().toString())) {
                        Utils.Log("InputProduksiActivity", "title : ${form.title} value : ${view.getValue()}")
                        return false
                    }
                }
            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        permissionHelper.onActivityForResult(requestCode)
        listForm.forEach { form ->
            val view = form.view
            if (view is FormUpload) {
                view.onResult(requestCode, resultCode, data)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onPermissionPreGranted(permissionsName: String) {

    }

    override fun onPermissionGranted(permissionName: Array<out String>) {
        initView()
    }

    override fun onNoPermissionNeeded() {

    }

    override fun onPermissionReallyDeclined(permissionName: String) {

    }

    override fun onPermissionDeclined(permissionName: Array<out String>) {

    }

    override fun onPermissionNeedExplanation(permissionName: String) {

    }

    override fun uploadImage() {
        listForm.forEach { form ->
            val view = form.view
            if (view is FormUpload) {
                if (TextUtils.isEmpty(view.photoLink) && !TextUtils.isEmpty(view.photoFileName)) {
                    view.uploadFile(object : TransferListener {
                        override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {

                        }

                        override fun onStateChanged(id: Int, state: TransferState?) {
                            if (state != null) {
                                if (state == TransferState.COMPLETED) {
                                    view.photoLink = "$AWS_FILE_URL${view.photoFileName}"
                                    uploadImage()
                                }
                            }
                        }

                        override fun onError(id: Int, ex: Exception?) {

                        }

                    })
                    return
                }
            }
        }
        presenter.saveDataProduct()
    }

    override fun getAllData(): MutableList<ModelForm> {
        return listForm
    }

    override fun finishSaveData() {

        val intent = Intent()
        intent.putExtra(IS_REFRESH, true)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun showLoading() {
        if (!loadingDialog.isShowing) {
            loadingDialog.show()
        }
    }

    override fun dissmissLoading() {
        if (loadingDialog.isShowing) {
            loadingDialog.dismiss()
        }
    }

    override fun setAllCommodity(data: MutableList<ModelComodity>?) {
        listCommodity.clear()
        if (data != null) {
            listCommodity.addAll(data)
        }
//        presenter.loadDistrict()
    }

    override fun setupView() {
        if (Utils.isNeedRuntimePermissions()) {
            permissionHelper.request(MULTI_PERMISSIONS)
        } else {
            initView()
        }
    }

}