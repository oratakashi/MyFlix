package com.oratakashi.myflix.ui.fav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.oratakashi.myflix.R
import com.oratakashi.myflix.databinding.ActivityFavoriteBinding
import com.oratakashi.myflix.ui.detail.DetailActivity
import com.oratakashi.viewbinding.core.binding.activity.viewBinding
import com.oratakashi.viewbinding.core.tools.showDefaultLayout
import com.oratakashi.viewbinding.core.tools.showEmptyLayout
import com.oratakashi.viewbinding.core.tools.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteActivity : AppCompatActivity() {

    private val adapter: FavoriteAdapter by lazy {
        FavoriteAdapter { data ->
            startActivity(DetailActivity::class.java) {
                it.putExtra("data", data)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            initObserver()
            rvFav.adapter = adapter
            toolbar.setNavigationOnClickListener { onBackPressed() }
        }
    }

    private fun initObserver() {
        with(binding) {
            lifecycleScope.launchWhenResumed {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.fav.collect {
                        if(it.isNotEmpty()) {
                            msvContent.showDefaultLayout()
                            adapter.submitData(it.map { data -> data.toDiscover() })
                        } else {
                            msvContent.showEmptyLayout()
                        }
                    }
                }
            }
        }
    }

    private val binding: ActivityFavoriteBinding by viewBinding()
    private val viewModel: FavoriteViewModel by viewModel()
}