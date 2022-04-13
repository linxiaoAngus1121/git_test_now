package com.example.gittest

import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

/**
 *
 * 请描述类的作用
 * @author linxiao date: 2022年4月12日, 0012 11:46
 */
class MainViewModel(private val repository: MainRepository): ViewModel(){

    //paging3
    val listData=Pager(PagingConfig(pageSize = 4)){
        val b = Looper.myLooper() == Looper.getMainLooper()
        Log.d("000", "是否是主线程$b")
        HomePageDataSource(
            RetrofitManager.getServices(
                IMainService::class.java,
                "https://reqres.in/"
            )
        )
    }.flow.flowOn(Dispatchers.IO).cachedIn(viewModelScope)


    //测试生命周期，所以使用retryWhen让她一直报错，一直重试
    suspend fun repeatTest(coroutineScope: CoroutineScope): Flow<ApiResponse> {
     return repository.getMain(1).flowOn(Dispatchers.IO).retryWhen { cause, attempt ->
            true
        }
    }

    //将flow转为stateFlow，然后配置 SharingStarted.WhileSubscribed（10秒）,再配合调用的地方MainActivity的（ mMainViewModel.testStateFlow(this).flowWithLifecycle(lifecycle,Lifecycle.State.STARTED)）
    //这样当我们app处于后台超过10秒，这样就能暂停flow发送数据，最正确的做法了
    suspend fun testStateFlow(coroutineScope: CoroutineScope): StateFlow<ApiResponse> {
        val apiResponse = ApiResponse(Support("", "", ""), mutableListOf(), 1, 1, 1, 1)
        return repository.getMain(1).retry(3){ cause-> //可以用这个实现重试次数，返回true代表需要重试，返回false就不需要重试，直接触发catch{},cause.cause我们可以根据类型判断本次是否需要重试
            cause.cause
            true
        }.retryWhen { cause, attempt ->     //也可以用retryWhen来判断，attempt为重试次数
            delay(3000L)                        //实现重试延迟
            val b = Looper.myLooper() == Looper.getMainLooper()
            Log.d("000", "是否是主线程$attempt")
            attempt<=3 //重试次数
        }.flowOn(Dispatchers.IO).catch {
            Log.d("000", "catch")
        }.stateIn(coroutineScope, SharingStarted.WhileSubscribed(10*1000),apiResponse)
    }
}