package me.nyaken.gitsearch.home

import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.nyaken.gitsearch.BaseActivity
import me.nyaken.gitsearch.R
import me.nyaken.gitsearch.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity: BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModels()

    override fun viewBinding() {
    }

    override fun setupObserve() {

    }

    override fun initLayout() {
        viewModel.doSearch()
    }
}