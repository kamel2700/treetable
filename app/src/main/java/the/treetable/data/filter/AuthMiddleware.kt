package the.treetable.data.filter

import okhttp3.Interceptor
import okhttp3.Response

class AuthMiddleware() : Interceptor {
    private val authManager = AuthManager()

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        authManager.getToken().let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }
}