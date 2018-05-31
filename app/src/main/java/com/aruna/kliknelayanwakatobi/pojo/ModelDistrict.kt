package com.aruna.kliknelayanwakatobi.pojo

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by marzellaalfamega on 12/15/17.
 */
open class ModelDistrict : RealmObject() {
    @PrimaryKey
    var id = ""

    var regency_id = ""
    var regency: ModelRegency? = null
    var name = ""

}