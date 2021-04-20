package the.treetable.data

import io.reactivex.Single
import retrofit2.http.GET
import the.treetable.data.response.RestResponse
import java.util.*

interface UserService {

    // returns current user id by his authorization
    @GET("/user/id")
    fun getCurrentUserId(): Single<RestResponse<UUID>>
}