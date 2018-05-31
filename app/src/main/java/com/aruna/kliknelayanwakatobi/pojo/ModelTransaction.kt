package com.aruna.kliknelayanwakatobi.pojo

/**
 * Created by marzellaalfamega on 12/14/17.
 */
class ModelTransaction {
/*
"fisher_member_data": {
                "fisherman_id": "327680",
                "fisherman_type": "tpi",
                "full_name": "Alya",
                "no_ktp": "582497577227",
                "no_fisherman_card": "",
                "no_sipi": "",
                "no_siup": "",
                "no_sikpi": "",
                "no_insurance": "",
                "production_type": ""
            },
 */
    var pickup_tools = ""
    var price = 0
    var pickup_geoloc : ModelGeoloc? = null
    var photo = ""

    var fisher_member_data : ModelAnggota? = null
}