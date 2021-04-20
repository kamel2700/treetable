package the.treetable.data.dto

import com.google.gson.annotations.SerializedName

data class Node(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("name")
    val name: String,
    @SerializedName("parent_id")
    val parentId: Long?,
    @SerializedName("project_id")
    val projectId: Int?,
    @SerializedName("children_ids")
    val childrenIds: List<Long>?,
    @SerializedName("props")
    val props: List<NodeProp>?
)