package jp.couplink.app.popup.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue


/**
 * Created by BinhTran on 2/12/2018.
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class Popup(val data: @RawValue Array<LoginInfo>?) : Parcelable

