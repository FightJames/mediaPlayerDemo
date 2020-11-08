package com.example.work.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.work.CastViewModelFactory
import com.example.work.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val castAdapter = CastAdapter()
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initViewModel()
        viewModel.fetchCasts()
    }

    private fun initView() {
        cast_list.let {
            it.layoutManager = GridLayoutManager(this, 2);
            it.adapter = castAdapter
        }
        cast_list_refresh.setOnRefreshListener {
            viewModel.fetchCasts()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            CastViewModelFactory(application)
        ).get(MainViewModel::class.java)
        viewModel.castsData.observe(this, Observer {
            cast_list_refresh.isRefreshing = false
            castAdapter.setData(it)
        })
    }
}