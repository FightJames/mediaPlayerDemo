package com.example.work.view.player

import com.example.work.util.BundleArgs

data class PlayArgs(
    val songUrl: String,
    val title: String,
    val largeImageUrl: String
) : BundleArgs