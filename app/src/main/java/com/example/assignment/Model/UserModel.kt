package com.example.assignment.Model

import android.os.Parcel
import android.os.Parcelable

data class UserModel(
    var id :String="",
    var name :String="",
    var email :String="",
    var number :Int = 0 ,
    var password :String = "" ,
    var url:String="",
    var imageName: String = ""
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readInt()?:0,
        parcel.readString()?:"",
        parcel.readString()?:"",

        )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeInt(number)
        parcel.writeString(password)
        parcel.writeString(url)
        parcel.writeString(imageName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserModel> {
        override fun createFromParcel(parcel: Parcel): UserModel {
            return UserModel(parcel)
        }

        override fun newArray(size: Int): Array<UserModel?> {
            return arrayOfNulls(size)
        }
    }
}