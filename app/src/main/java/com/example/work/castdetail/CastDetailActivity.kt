package com.example.work.castdetail

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.work.CastViewModelFactory
import com.example.work.R
import com.example.work.service.PlayService
import kotlinx.android.synthetic.main.activity_cast_detail.*

class CastDetailActivity : AppCompatActivity() {
    lateinit var viewModel: CastDetailViewModel
    private val castDetailadapter = CastDetailAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cast_detail)
        initPlayService()
        initView()
        initViewModel()
        viewModel.fetchCastDetails()
    }

    private fun initView() {
        cast_detail_list.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = castDetailadapter
        }
        detail_back_image.setOnClickListener { finish() }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            CastViewModelFactory(application)
        ).get(CastDetailViewModel::class.java)
        viewModel.castDetailsData.observe(this, Observer {
            castDetailadapter.setData(it.contents, it.artworkLargeImageUrl)
            Glide.with(this)
                .load(it.artworkSmallImageUrl)
                .fitCenter()
                .into(detail_cast_image)
            detail_cast_name.text = it.name
            detail_cast_artist_name.text = it.artistName
        })
    }

    private fun initPlayService() {
        startService(PlayService.getIntent(this))
        bindService(PlayService.getIntent(this), object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                castDetailadapter.playService = (service as? PlayService.PlayBinder)?.getPlayService()
            }
        }, Context.BIND_AUTO_CREATE)
    }


    companion object {
        fun getIntent(context: Context): Intent =
            Intent(context, CastDetailActivity::class.java)
    }
}