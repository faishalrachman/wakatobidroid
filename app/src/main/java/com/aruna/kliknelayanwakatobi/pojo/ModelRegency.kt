package com.aruna.kliknelayanwakatobi.pojo

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by marzellaalfamega on 12/12/17.
 */
open class ModelRegency : RealmObject() {
    @PrimaryKey
    var id = ""
    var province_id = ""
    var name = ""

}