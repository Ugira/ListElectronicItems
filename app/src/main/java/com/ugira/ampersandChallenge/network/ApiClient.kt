package com.ugira.ampersandChallenge.network

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.ugira.ampersandChallenge.structure.ElectronicItem
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

object ApiClient {

    private const val BASE_URL = "https://api.restful-api.dev/"
    val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    suspend fun getObjects(): List<ElectronicItem> {
        var toReturn: List<ElectronicItem> = emptyList<ElectronicItem>()
        try {

            return apiService.getObjects()
        } catch (e: Exception) {
            // Handle error
            println("Error getting api objects: $e")
            e.printStackTrace()

        }
        return toReturn;
    }

    fun manualObjects(jsonString: String): List<ElectronicItem>? {
        // Initialize Moshi
        val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
            .build()

        // Define the type of your object list
        val listType = Types.newParameterizedType(List::class.java, ElectronicItem::class.java)

        // Create a JsonAdapter for the list of objects
        val adapter: JsonAdapter<List<ElectronicItem>> = moshi.adapter(listType)

        // Parse the JSON string into a list of objects

        // Parse the JSON string into a list of objects
        val objects: List<ElectronicItem>? = adapter.fromJson(jsonString)

        // Print objects to verify
        objects?.forEach { obj ->
            println("Object: ${obj.id}, ${obj.title}, ${obj.spec}")
        }
        return adapter.fromJson(jsonString)
    }
}





