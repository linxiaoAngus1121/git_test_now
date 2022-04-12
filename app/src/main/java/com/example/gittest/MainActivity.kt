package com.example.gittest

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 *
 * https://reqres.in/api/users?page=1
 * @author linxiao date: 2022年4月12日, 0012 11:45
 */
class MainActivity : AppCompatActivity() {

    private val mMainViewModel by viewModel<MainViewModel>()
    var findViewById: RecyclerView? = null
    lateinit var mainListAdapter: MainListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById = findViewById(R.id.rv_content)

        mainListAdapter = MainListAdapter()
        mainListAdapter.addLoadStateListener {
            if (it.refresh == LoadState.Loading) {
                Toast.makeText(this, "加载中", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "停止加载", Toast.LENGTH_LONG).show()
            }
        }
        findViewById?.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mainListAdapter
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mMainViewModel.listData.collectLatest {
                    mainListAdapter.submitData(it)
                }
            }
        }

        lifecycleScope.launch {
            //或者flow调用flowWithLifecycle()也可以
               mMainViewModel.repeatTest(this).flowWithLifecycle(lifecycle,Lifecycle.State.STARTED).collectLatest {

           }
        }
    }
}