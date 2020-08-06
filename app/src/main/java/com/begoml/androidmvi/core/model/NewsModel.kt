package com.begoml.androidmvi.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsModel(
    val id: Int,
    val title: String,
    val description: String
) : Parcelable