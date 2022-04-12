package com.example.gittest

import com.google.gson.annotations.SerializedName

/**
 *
 * 请描述类的作用
 * @author linxiao date: 2022年4月12日, 0012 13:57
 */
data class ApiResponse(
    val support: Support,
    @SerializedName(value = "data")
    val myData: MutableList<Data>,
    val page: Int,
    @SerializedName(value = "per_page")
    val perPage: Int,
    val total: Int,
    @SerializedName(value = "total_pages")
    val totalPages: Int
)

data class Support(
    val company: String,
    val text: String,
    val url: String
)

data class Data(
    val avatar: String,
    val email: String,
    @SerializedName(value = "first_name")
    val firstName: String,
    val id: Int,
    @SerializedName(value = "last_name")
    val lastName: String
)