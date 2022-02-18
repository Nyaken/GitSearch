package me.nyaken.gitsearch.home

import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.nyaken.common.SearchSort
import me.nyaken.gitsearch.BaseActivity
import me.nyaken.gitsearch.R
import me.nyaken.gitsearch.databinding.ActivityMainBinding
import me.nyaken.utils.EventObserver

@AndroidEntryPoint
class MainActivity: BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModels()

    override fun viewBinding() {
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override fun setupObserve() {
        viewModel.showErrorToast.observe(this, EventObserver{
            Toast.makeText(this, getString(it), Toast.LENGTH_SHORT).show()
        })
    }

    override fun initLayout() {
        binding.spinnerSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.selectedSortKey(parent?.getItemAtPosition(position) as SearchSort)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
}