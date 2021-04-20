package the.treetable.data.filter

import okhttp3.Interceptor
import okhttp3.Response

class HostSelectorMiddleware() : Interceptor {

    var apiBasePath: String = ""

    override fun intercept(chain: Interceptor.Chain): Response {
        val newUrl = chain.request()
            .url()
            .toString()
            .replace("http://__base_url__/", apiBasePath)
        return chain.proceed(
            chain.request()
                .newBuilder()
                .url(newUrl)
                .build()
        )
    }
}