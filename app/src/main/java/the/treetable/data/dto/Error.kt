package the.treetable.data.dto

import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("msg")
    val msg: String,
    @SerializedName("info")
    val info: Map<String, Any>
)