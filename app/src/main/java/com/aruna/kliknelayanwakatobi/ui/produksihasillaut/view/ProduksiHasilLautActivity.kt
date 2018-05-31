package com.aruna.kliknelayanwakatobi.ui.produksihasillaut.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import apri.aruna.com.apriapp.ui.fishlog.fishloglist.view.AdProduksiHasilLaut
import com.aruna.kliknelayanwakatobi.R
import com.aruna.kliknelayanwakatobi.interfacecustom.OnClickRecyclerItem
import com.aruna.kliknelayanwakatobi.pojo.ModelTransaction
import com.aruna.kliknelayanwakatobi.pojo.ModelUser
import com.aruna.kliknelayanwakatobi.tools.*
import com.aruna.kliknelayanwakatobi.ui.BaseActivityWithUp
import com.aruna.kliknelayanwakatobi.ui.inserthasilproduksi.view.InputHasilProduksiActivity
import com.aruna.kliknelayanwakatobi.ui.produksihasillaut.model.ProduksiHasilLautModel
import com.aruna.kliknelayanwakatobi.ui.produksihasillaut.presenter.IProduksiHasilLautPresenter
import com.aruna.kliknelayanwakatobi.ui.produksihasillaut.presenter.ProduksiHasilLautPresenter
import kotlinx.android.synthetic.main.activity_produksi_hasil_laut.*
import kotlinx.android.synthetic.main.default_toolbar.*


class ProduksiHasilLautActivity : BaseActivityWithUp(), IProduksiHasilLautView, OnClickRecyclerItem, TextWatcher {
    private val REQUEST_INSERT_DATA = 123

    private var HANDLER_ACTIVE = true
    //    private val trigerHandler = Handler()
//    private var runnableHandler: Runnable? = null
    private val listAllTransaction = mutableListOf<ModelTransaction>()
    private val adapter = AdProduksiHasilLaut(mutableListOf(), this)
    private val presenter: IProduksiHasilLautPresenter
    val suggestion = mutableListOf<String>()
    //    val listAnggota = mutableListOf<ModelAnggota>()
    var modelUser: ModelUser? = null

//    private var isFromSuggestion = false

    lateinit var adapterSugestion: ArrayAdapter<String>

    init {
        presenter = ProduksiHasilLautPresenter(this, ProduksiHasilLautModel())
        presenter.setupModelListener()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produksi_hasil_laut)

        getModelUser()

        initView()
        presenter.getEnumeratorData()
    }

    private fun getModelUser() {
        if (intent.extras.containsKey(EXTRA_MODEL_USER)) {
            modelUser = intent.extras.getParcelable(EXTRA_MODEL_USER)
        }
    }

    private fun initView() {
        setupToolbar(myToolbar)

        rvProduksiHasilLaut.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvProduksiHasilLaut.adapter = adapter

        btnInputHasilProduksiLaut.setOnClickListener { gotoInputData() }

        adapterSugestion = ArrayAdapter(this, android.R.layout.select_dialog_item, suggestion)

        edtSearchAnggota.addTextChangedListener(this)
        edtSearchAnggota.threshold = 1
//        edtSearchAnggota.setOnItemClickListener { _, _, position, _ ->
//            onSuggestionClicked(position)
//        }

        edtSearchAnggota.setAdapter(adapterSugestion)

        btnSearchProduksiHasilLaut.setOnClickListener { }
    }

//    private fun onSuggestionClicked(position: Int) {
//        isFromSuggestion = true
//        presenter.getFishermanData(position)
//    }

    private fun gotoInputData() {
        val intent = Intent(this, InputHasilProduksiActivity::class.java)
        intent.putExtra(EXTRA_FORM_TYPE, modelUser?.user_group_id)
        startActivityForResult(intent, REQUEST_INSERT_DATA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_INSERT_DATA) {
                if (data?.hasExtra(IS_REFRESH) == true) {
                    if (data.getBooleanExtra(IS_REFRESH, false)) {
                        presenter.getEnumeratorData()
                    }
                }
            }
        }

    }

    override fun onItemClick(view: View?, position: Int) {

    }

    override fun afterTextChanged(p0: Editable?) {

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        adapter.listTransaction.clear()
        if (!TextUtils.isEmpty(p0)) {
            listAllTransaction.forEach { trans ->
                val name = trans.fisher_member_data?.full_name ?: ""
                if (name.contains(p0.toString())) {
                    adapter.listTransaction.add(trans)
                }
            }
        } else {
            adapter.listTransaction.addAll(listAllTransaction)
        }
        Utils.Log("ProduksiHasilLaut", "isi adapter ${adapter.listTransaction.size}")
        adapter.notifyDataSetChanged()
    }

//    private fun startWait() {
//        if (runnableHandler != null) {
//            trigerHandler.removeCallbacks(runnableHandler)
//        }
//        runnableHandler = Runnable { searchAnggota() }
//        trigerHandler.postDelayed(runnableHandler, WAIT)
//    }

//    private fun searchAnggota() {
//        if (!isFromSuggestion) {
//            presenter.searchAnggota()
//        } else {
//            isFromSuggestion = false
//        }
//    }

    override fun getQuery(): String {
        return edtSearchAnggota.text.toString()
    }

//    override fun updateSuggestion(listModelAnggota: MutableList<ModelAnggota>) {
//        suggestion.clear()
//        this.listAnggota.clear()
//        listModelAnggota.forEach { modelAnggota ->
//            this.listAnggota.add(modelAnggota)
//            suggestion.add(modelAnggota.full_name)
//        }
//        adapterSugestion = ArrayAdapter(this, android.R.layout.select_dialog_item, suggestion)
//        edtSearchAnggota.setAdapter(adapterSugestion)
//        edtSearchAnggota.showDropDown()
//    }

//    override fun clearSuggestion() {
//        suggestion.clear()
//        this.listAnggota.clear()
//        adapterSugestion = ArrayAdapter(this, android.R.layout.select_dialog_item, suggestion)
//        edtSearchAnggota.setAdapter(adapterSugestion)
//        edtSearchAnggota.showDropDown()
//    }

    override fun getFishermanId(position: Int): String {
        return USER_DATA?.user?.id ?: ""
    }

    override fun successGetDataTransaction(data: MutableList<ModelTransaction>) {
        listAllTransaction.clear()
        listAllTransaction.addAll(data)

        adapter.listTransaction.clear()
        adapter.listTransaction.addAll(data)
        adapter.notifyDataSetChanged()

        data.forEach { transaction ->
            val name = transaction.fisher_member_data?.full_name ?: ""
            if (!TextUtils.isEmpty(name) && !suggestion.contains(name)) {
                suggestion.add(name)
            }
        }

        adapterSugestion = ArrayAdapter(this, android.R.layout.select_dialog_item, suggestion)
        edtSearchAnggota.setAdapter(adapterSugestion)
    }

}