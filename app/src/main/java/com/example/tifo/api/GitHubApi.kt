package com.example.tifo.api

import com.example.tifo.data.DataModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

data class RepositoriesResponse (
    val totalCount : Int? = null,
    val incomplete_resilts : Boolean? = null,
    val items : List<ResponseRepositoryClass> = listOf()
)

data class ResponseRepositoryClass(
    val id : Int,
    val full_name : String? = null,
    val description : String? = null,
    val language : String? = null,
    val stargazers_count : String? = null,
    val contributors_url : String? = null,
    val branches_url : String
)



object GitHubApi {

    interface ApiService {


        @GET("search/repositories")
        suspend fun listRepos(@Query("q") user: String?, @Query("per_page") page : Int = 10): Response<RepositoriesResponse>

        @GET("repos/{owner}/branches")
        suspend fun getBranches(@Path(value = "owner", encoded = true) owner: String?): Response<List<DataModel.Branch>>


        @GET("repos/{owner}/contributors")
        suspend fun getContributors(@Path("owner", encoded = true) owner: String?): Response<List<DataModel.User>>

    }


    private const val BASE_URL: String = "https://api.github.com/"

    private val gson : Gson by lazy {
        GsonBuilder().setLenient().create()
    }

    private val httpClient : OkHttpClient by lazy {
        OkHttpClient.Builder().build()
    }

    private val retrofit : Retrofit by lazy {
        Retrofit.Builder()
            .client(httpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val apiService :  ApiService by lazy{
        retrofit.create(ApiService::class.java)
    }
}

