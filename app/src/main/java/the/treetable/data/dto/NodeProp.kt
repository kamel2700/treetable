package the.treetable.data.dto

import com.google.gson.annotations.SerializedName

data class NodeProp(
    @SerializedName("name")
    val name: String,
    @SerializedName("alias")
    val alias: String?,
    @SerializedName("value")
    val value: Any
)