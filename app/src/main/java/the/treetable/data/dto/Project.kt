package the.treetable.data.dto

import com.google.gson.annotations.SerializedName

data class Project(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("root_id")
    val rootId: Long?
)
