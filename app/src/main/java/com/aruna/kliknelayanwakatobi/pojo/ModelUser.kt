package com.aruna.kliknelayanwakatobi.pojo

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by marzellaalfamega on 12/8/17.
 */
class ModelUser() : Parcelable{
    /*
    {"id":"196608",
            "user_group_id":"196608",
            "regency_id":"",
            "user_group":
                {"id":"196608",
                "user_group_name":"",
                "description":""},
            "username":"alfaWakatobi",
            "password":"$2a$10$Y0npHFFhOc4MSZl1HLSODuUQbKSndDYN6g3OQS7z9Gs4RDQ2fzeri",
            "avatar":"",
            "email":"patriot@gmail.com",
            "address":"jl. pegangsaan timur",
            "phone":"0856839394",
            "full_name":"Khadrogo",
            "description":"",
            "latlng":"-6.178763, 107.065758",
            "is_active":1,
            "created_at":"2017-12-08T10:13:23Z"},
     */
    var id = ""
    var user_group_id = ""
    var regency_id = ""
    var user_group : ModelUserGroup? = null
    var username = ""
    var avatar = ""
    var email = ""
    var address = ""
    var phone = ""
    var full_name = ""
    var description = ""
    var latlng = ""
    var is_active = 0
    var created_at = ""

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        user_group_id = parcel.readString()
        regency_id = parcel.readString()
        username = parcel.readString()
        avatar = parcel.readString()
        email = parcel.readString()
        address = parcel.readString()
        phone = parcel.readString()
        full_name = parcel.readString()
        description = parcel.readString()
        latlng = parcel.readString()
        is_active = parcel.readInt()
        created_at = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(user_group_id)
        parcel.writeString(regency_id)
        parcel.writeString(username)
        parcel.writeString(avatar)
        parcel.writeString(email)
        parcel.writeString(address)
        parcel.writeString(phone)
        parcel.writeString(full_name)
        parcel.writeString(description)
        parcel.writeString(latlng)
        parcel.writeInt(is_active)
        parcel.writeString(created_at)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ModelUser> {
        override fun createFromParcel(parcel: Parcel): ModelUser {
            return ModelUser(parcel)
        }

        override fun newArray(size: Int): Array<ModelUser?> {
            return arrayOfNulls(size)
        }
    }
}