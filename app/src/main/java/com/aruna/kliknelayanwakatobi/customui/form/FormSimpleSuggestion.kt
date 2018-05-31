package com.aruna.kliknelayanwakatobi.customui.form

import android.content.Context
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import com.aruna.kliknelayanwakatobi.R
import com.aruna.kliknelayanwakatobi.customui.AdapterSuggestionContains
import com.aruna.kliknelayanwakatobi.httprequest.MyCallback
import com.aruna.kliknelayanwakatobi.httprequest.RetrofitAPI
import com.aruna.kliknelayanwakatobi.pojo.*
import com.aruna.kliknelayanwakatobi.tools.FISHERMAN_DATA
import com.aruna.kliknelayanwakatobi.tools.Utils
import com.aruna.kliknelayanwakatobi.tools.WAIT
import retrofit2.Response

/**
 * Created by marzellaalfamega on 12/5/17.
 */
class FormSimpleSuggestion(context: Context, val title: String, var listSuggestion: MutableList<Any> = mutableListOf(), threshHold: Int = 1) : FormBase(context, title), TextWatcher, AdapterView.OnItemClickListener {

    var edtValue: AutoCompleteTextView

    private val trigerHandler = Handler()
    private var runnableHandler: Runnable? = null
    val suggestion = mutableListOf<String>()
    var selectedItem: Any? = null
//    val listAnggota = mutableListOf<ModelAnggota>()

    private var isFromItemSelected = false

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.form_simple_with_suggestion, null, false)

        val tvTitle = view.findViewById<TextView>(R.id.tvTitleFormSimpleWithSuggestion)
        tvTitle.text = title

        edtValue = view.findViewById(R.id.edtValueFormSimpleWithSuggestion)
        if (listSuggestion.size == 0) {
            edtValue.addTextChangedListener(this)
        } else {
            suggestion.clear()
            listSuggestion.forEach { modelSuggestion ->
                if (modelSuggestion is ModelComodity) {
                    suggestion.add(modelSuggestion.commodity_name)
                } else if (modelSuggestion is ModelDistrict) {
                    suggestion.add(modelSuggestion.name)
                } else if (modelSuggestion is ModelSubDistrict) {
                    suggestion.add(modelSuggestion.name)
                }
            }
            val adapterSugestion = AdapterSuggestionContains(context, android.R.layout.select_dialog_item, suggestion)

            edtValue.setAdapter(adapterSugestion)
            edtValue.threshold = threshHold
        }
        edtValue.onItemClickListener = this
        addView(view)
    }

    override fun getValue(): Any {
        return selectedItem ?: ""
    }

    override fun afterTextChanged(p0: Editable?) {

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        Utils.Log("FormSimpleSuggestion", "text change : ${p0.toString()}")
        startWait()
    }

    private fun startWait() {
        if (runnableHandler != null) {
            trigerHandler.removeCallbacks(runnableHandler)
        }
        runnableHandler = Runnable { searchAnggota() }
        trigerHandler.postDelayed(runnableHandler, WAIT)
    }

    private fun searchAnggota() {
        if (!isFromItemSelected) {
            val condition = "full_name.icontains:${edtValue.text}"
            Utils.Log("FormSimpleSuggestion", condition)
            RetrofitAPI.wakatobiApi.searchAnggota(condition).enqueue(object : MyCallback<ModelAnggotaData>() {
                override fun onSuccess(response: Response<*>) {
                    suggestion.clear()
                    listSuggestion.clear()
                    val body = response.body()
                    if (body != null) {
                        if (body is ModelAnggotaData) {
                            body.data?.forEach { modelAnggota ->
                                suggestion.add(modelAnggota.full_name)
                                listSuggestion.add(modelAnggota)
                            }
                        }
                    }

                }

                override fun onMessage(msg: String) {

                }

                override fun onFinish() {
                    val adapterSugestion = ArrayAdapter(context, android.R.layout.select_dialog_item, suggestion)

                    edtValue.setAdapter(adapterSugestion)
                    edtValue.showDropDown()
                }

            })
        } else {
            isFromItemSelected = false
        }
    }

    override fun onItemClick(adapter: AdapterView<*>?, view: View?, position: Int, id: Long) {
        isFromItemSelected = true
        val data = listSuggestion[0]
        if (data is ModelAnggota) {
            FISHERMAN_DATA = data
            listSuggestion.forEach { sugest ->
                val item = sugest as ModelAnggota
                if (item.full_name == edtValue.text.toString()) {
                    selectedItem = item
                    return
                }
            }
        } else if (data is ModelComodity) {
            listSuggestion.forEach { sugest ->
                val item = sugest as ModelComodity
                if (item.commodity_name == edtValue.text.toString()) {
                    selectedItem = item
                    return
                }
            }
        } else if (data is ModelDistrict) {
            listSuggestion.forEach { sugest ->
                val item = sugest as ModelDistrict
                if (item.name == edtValue.text.toString()) {
                    selectedItem = item
                    Utils.Log("FormSimpleSuggestion", "selected item district ${item.name}")
                    return
                }
            }
        } else if (data is ModelSubDistrict) {
            listSuggestion.forEach { sugest ->
                val item = sugest as ModelSubDistrict
                if (item.name == edtValue.text.toString()) {
                    selectedItem = item
                    Utils.Log("FormSimpleSuggestion", "selected item sub district ${item.name}")
                    return
                }
            }
        }
    }

}