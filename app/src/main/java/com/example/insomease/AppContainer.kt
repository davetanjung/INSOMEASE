package com.example.insomease

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.insomease.repositories.AuthenticationRepository
import com.example.insomease.repositories.NetworkAuthenticationRepository
import com.example.insomease.repositories.NetworkUserRepository
import com.example.insomease.repositories.UserRepository
//import com.example.todolistapp.repositories.NetworkTodoRepository
//import com.example.todolistapp.repositories.NetworkUserRepository
//import com.example.todolistapp.repositories.TodoRepository
//import com.example.todolistapp.repositories.UserRepository
import com.example.insomease.services.AuthenticationAPIService
import com.example.insomease.services.UserAPIService
//import com.example.todolistapp.services.TodoAPIService
//import com.example.todolistapp.services.UserAPIService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// A container is an object that contains the dependencies that the app requires.
// These dependencies are used across the whole application, so they need to be in a common place that all activities can use.
// You can create a subclass of the Application class and store a reference to the container.
interface AppContainer {
    val authenticationRepository: AuthenticationRepository
    val userRepository: UserRepository
//    val todoRepository: TodoRepository
}

class DefaultAppContainer(
    private val userDataStore: DataStore<Preferences>
): AppContainer {
    // change it to your own local ip please
    private val baseUrl = "http://192.168.0.106:3000/"

    // RETROFIT SERVICE
    // delay object creation until needed using lazy
    private val authenticationRetrofitService: AuthenticationAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(AuthenticationAPIService::class.java)
    }

    private val userAPIService: UserAPIService by lazy {
        val retrofit = initRetrofit()
        retrofit.create(UserAPIService::class.java)
    }

    // REPOSITORY INIT
    // Passing in the required objects is called dependency injection (DI). It is also known as inversion of control.
    override val authenticationRepository: AuthenticationRepository by lazy {
        NetworkAuthenticationRepository(authenticationRetrofitService)
    }
    override val userRepository: UserRepository by lazy {
        NetworkUserRepository(
            userAPIService = userAPIService,
            userDataStore = userDataStore
        )
    }

    private fun initRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = (HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
        client.addInterceptor(logging)

        return Retrofit
            .Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .client(client.build())
            .baseUrl(baseUrl)
            .build()
    }
}