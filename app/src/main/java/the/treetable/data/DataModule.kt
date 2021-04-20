package the.treetable.data

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import the.treetable.data.filter.AuthMiddleware
import the.treetable.data.filter.HostSelectorMiddleware
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    @Named("baseUrl")
    fun baseUrl(): String {
        return "http://__base_url__/";
    }

    @Provides
    @Singleton
    fun authService(@Named("withoutAuth") retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun treeService(@Named("withAuth") retrofit: Retrofit): TreeService {
        return retrofit.create(TreeService::class.java)
    }

    @Provides
    @Singleton
    fun userService(@Named("withAuth") retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun hostSelectorMiddleware(): HostSelectorMiddleware {
        return HostSelectorMiddleware()
    }

    @Provides
    @Singleton
    @Named("withAuth")
    fun okHttpClientWithAuth(hostSelectorMiddleware: HostSelectorMiddleware): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(hostSelectorMiddleware)
            .addInterceptor(AuthMiddleware())
            .build()
    }

    @Provides
    @Singleton
    @Named("withoutAuth")
    fun okHttpClientWithoutAuth(hostSelectorMiddleware: HostSelectorMiddleware): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(hostSelectorMiddleware)
            .build()
    }


    @Provides
    @Singleton
    @Named("withAuth")
    fun retrofitWithAuth(
        @Named("baseUrl") baseUrl: String,
        @Named("withAuth") okHttpClientWithAuth: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClientWithAuth)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("withoutAuth")
    fun retrofitWithoutAuth(
        @Named("baseUrl") baseUrl: String,
        @Named("withoutAuth") okHttpClientWithoutAuth: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClientWithoutAuth)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}