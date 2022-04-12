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
import kotlinx.coroutines.flow.*

/**
 *
 * 请描述类的作用
 * @author linxiao date: 2022年4月12日, 0012 11:46
 */
class MainViewModel(private val repository: MainRepository): ViewModel(){

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


    suspend fun repeatTest(coroutineScope: CoroutineScope): Flow<ApiResponse> {
     return repository.getMain(1).flowOn(Dispatchers.IO).retryWhen { cause, attempt ->
            true
        }
    }
}