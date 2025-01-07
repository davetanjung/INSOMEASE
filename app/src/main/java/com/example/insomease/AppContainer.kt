package com.example.insomease

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.insomease.repositories.ActivityRepository
import com.example.insomease.repositories.AuthenticationRepository
import com.example.insomease.repositories.CategoryRepository
import com.example.insomease.repositories.NetworkActivityRepository
import com.example.insomease.repositories.NetworkAuthenticationRepository
import com.example.insomease.repositories.NetworkCategoryRepository
import com.example.insomease.repositories.NetworkSleepNoteRepository
import com.example.insomease.repositories.NetworkUserRepository
import com.example.insomease.repositories.SleepNoteRepository
import com.example.insomease.repositories.UserRepository
import com.example.insomease.services.ActivityAPIService
//import com.example.todolistapp.repositories.NetworkTodoRepository
//import com.example.todolistapp.repositories.NetworkUserRepository
//import com.example.todolistapp.repositories.TodoRepository
//import com.example.todolistapp.repositories.UserRepository
import com.example.insomease.services.AuthenticationAPIService
import com.example.insomease.services.CategoryService
import com.example.insomease.services.SleepNoteAPI
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
    val activityRepository: ActivityRepository
    val categoryRepository: CategoryRepository
    val sleepNoteRepository: SleepNoteRepository

}

class DefaultAppContainer(
    private val userDataStore: DataStore<Preferences>
): AppContainer {
    // change it to your own local ip please
    private val baseUrl = "http://10.0.52.149:3000/"
    // RETROFIT SERVICE
    // delay object creation until needed using lazy
    private val authenticationRetrofitService: AuthenticationAPIService by lazy {
        val retrofit = initRetrofit()

        retrofit.create(AuthenticationAPIService::class.java)
    }

    private val sleepNoteAPIService: SleepNoteAPI by lazy {
        val retrofit = initRetrofit()
        retrofit.create(SleepNoteAPI::class.java)
    }


    private val userAPIService: UserAPIService by lazy {
        val retrofit = initRetrofit()
        retrofit.create(UserAPIService::class.java)
    }

    private val activityAPIService: ActivityAPIService by lazy { // Add this service
        val retrofit = initRetrofit()
        retrofit.create(ActivityAPIService::class.java)
    }

    private val categoryService: CategoryService by lazy {
        val retrofit = initRetrofit()
        retrofit.create(CategoryService::class.java)
    }



    // REPOSITORY INIT
    override val authenticationRepository: AuthenticationRepository by lazy {
        NetworkAuthenticationRepository(authenticationRetrofitService)
    }
    override val userRepository: UserRepository by lazy {
        NetworkUserRepository(
            userAPIService = userAPIService,
            userDataStore = userDataStore
        )
    }

    override val activityRepository: ActivityRepository by lazy {
        NetworkActivityRepository(activityAPIService)
    }

    override val sleepNoteRepository: SleepNoteRepository by lazy {
        NetworkSleepNoteRepository(sleepNoteAPIService)
    }

    override val categoryRepository: CategoryRepository by lazy {
        NetworkCategoryRepository(categoryService)
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