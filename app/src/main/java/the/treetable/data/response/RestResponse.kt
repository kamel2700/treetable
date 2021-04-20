package the.treetable.data.response

import com.google.gson.annotations.SerializedName
import the.treetable.data.dto.Error

data class RestResponse<T>(
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    val data: T?,
    @SerializedName("error")
    val error: Error?
)