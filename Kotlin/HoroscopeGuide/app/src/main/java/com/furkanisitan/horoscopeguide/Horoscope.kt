package com.furkanisitan.horoscopeguide

import android.os.Parcel
import android.os.Parcelable


data class Horoscope(var name: String?, var date: String?, var generalFeatures: String?,
                     var imageId: Int, var imageBigId: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(date)
        parcel.writeString(generalFeatures)
        parcel.writeInt(imageId)
        parcel.writeInt(imageBigId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Horoscope> {
        override fun createFromParcel(parcel: Parcel): Horoscope {
            return Horoscope(parcel)
        }

        override fun newArray(size: Int): Array<Horoscope?> {
            return arrayOfNulls(size)
        }
    }
}

