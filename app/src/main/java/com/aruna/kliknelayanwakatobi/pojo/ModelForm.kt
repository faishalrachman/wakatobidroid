package com.aruna.kliknelayanwakatobi.pojo

import android.support.v4.app.FragmentManager
import android.text.InputType
import android.view.View
import com.aruna.kliknelayanwakatobi.customenum.EnumFormType
import com.aruna.kliknelayanwakatobi.customui.form.FormBase
import com.aruna.kliknelayanwakatobi.customui.form.FormSimpleSuggestion
import com.aruna.kliknelayanwakatobi.tools.Utils
import org.json.JSONObject

/**
 * Created by marzellaalfamega on 12/5/17.
 */
class ModelForm(val paramKey: String, val title: String, val formType: EnumFormType = EnumFormType.SIMPLE, val satuan: String = "",
                val listener: View.OnClickListener? = null, val fragmentManager: FragmentManager? = null,
                var view: FormBase? = null, val inputType: Int = InputType.TYPE_CLASS_TEXT, val listSuggestion: MutableList<Any>? = null,
                val threshold: Int = 1) {

    fun getValue(): JSONObject {
        val json = JSONObject()

        if (formType == EnumFormType.SIMPLE_WITH_SUGGESTION) {
            val viewSpesific = view as FormSimpleSuggestion
            val value = viewSpesific.getValue()

            Utils.Log("ModelForm", "form type suggestion ${viewSpesific.title}")
            when (value) {
                is ModelComodity -> {
                    json.put("commodity_id", value.id)
                    json.put("commodity", value.commodity_name)
                    Utils.Log("ModelForm", "commodity ${value.commodity_name}")
                }
                is ModelDistrict -> {
                    json.put(paramKey, value.name)
                    Utils.Log("ModelForm", "district ${value.name}")
                }
                is ModelSubDistrict -> {
                    json.put(paramKey, value.name)
                    Utils.Log("ModelForm", "sub district ${value.name}")
                }
            }
            Utils.Log("ModelForm", "json ${json.toString()}")
        }
        return json
    }
}