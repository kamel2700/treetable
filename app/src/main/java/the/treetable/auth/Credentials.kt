package the.treetable.auth

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Credentials(
    val apiBaseUrl: String,
    val username: String,
    val password: String,
    val token: String
) : Parcelable