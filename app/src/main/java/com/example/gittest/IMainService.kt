package com.example.gittest

import com.example.gittest.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 * 请描述类的作用
 * @author linxiao date: 2022年4月12日, 0012 13:54
 */
interface IMainService {

    @GET("api/users")
    suspend fun getListData(@Query("page")index:Int,@Query("per_page")perPage:Int): ApiResponse
}