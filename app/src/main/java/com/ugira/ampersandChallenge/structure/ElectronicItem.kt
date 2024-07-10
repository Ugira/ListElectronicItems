package com.ugira.ampersandChallenge.structure

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.json.JSONArray

@JsonClass(generateAdapter = true)
data class ElectronicItem(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val title: String,
    @Json(name = "data") val spec: Map<String, Any>?
)