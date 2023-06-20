package com.oishikenko.android.recruitment.data.model


import com.squareup.moshi.Json

data class CookingRecord(
    @Json(name = "comment")
    val comment: String,
    @Json(name = "image_url")
    val imageUrl: String,
    @Json(name = "recipe_type")
    val recipeType: String,
    @Json(name = "recorded_at")
    val recordedAt: String
)

private const val secTextCount = 3
val CookingRecord.datetime: String
    get() = recordedAt
        .replace("-", "/")
        .dropLast(secTextCount)

private val recipeTypeMap = mapOf(
    "main_dish" to "主菜/主食",
    "side_dish" to "副菜",
    "soup" to "スープ"
)
val CookingRecord.recipeText: String
    get() = recipeTypeMap[recipeType]!!