package the.treetable.data.dto

import com.google.gson.annotations.SerializedName

data class SignInData(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String
)