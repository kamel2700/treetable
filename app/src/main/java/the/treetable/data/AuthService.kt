package the.treetable.data

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import the.treetable.data.dto.SignInData
import the.treetable.data.dto.SignUpData
import the.treetable.data.response.RestResponse

interface AuthService {

    // creates new user and returns valid authorization token
    @POST("/auth/sign_up")
    fun doSignUp(@Body signInData: SignUpData): Single<RestResponse<String>>

    // creates authorization token for an existing user by his credentials
    @POST("/auth/sign_in")
    fun doSignIn(@Body signInData: SignInData): Single<RestResponse<String>>
}