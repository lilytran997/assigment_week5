package com.example.assigment_week5

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by nampham on 2019-05-02.
 */
@Parcelize
data class Item(
    var imageId: Int,
    var title: String,
    var price: Double,
    var category: Category
) : Parcelable