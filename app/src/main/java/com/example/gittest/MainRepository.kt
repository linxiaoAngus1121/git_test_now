package com.example.gittest

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 *
 * 请描述类的作用
 * @author linxiao date: 2022年4月12日, 0012 13:53
 */
class MainRepository {
    suspend fun getMain(index: Int) = flow<ApiResponse> {
        //正确的接口是https://reqres.in/,这里改成on是为了故意让接口失败
        RetrofitManager.getServices(IMainService::class.java, "https://reqres.on/")
            .getListData(index,3)
    }.flowOn(Dispatchers.IO)
}