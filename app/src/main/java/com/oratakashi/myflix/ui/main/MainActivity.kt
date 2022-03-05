package com.oratakashi.myflix.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.google.android.material.tabs.TabLayoutMediator
import com.oratakashi.myflix.customview.CategoriesView
import com.oratakashi.myflix.data.model.discover.DataDiscover
import com.oratakashi.myflix.databinding.ActivityMainBinding
import com.oratakashi.myflix.ui.detail.DetailActivity
import com.oratakashi.myflix.ui.fav.FavoriteActivity
import com.oratakashi.viewbinding.core.binding.activity.viewBinding
import com.oratakashi.viewbinding.core.tools.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val slider: SliderAdapter by lazy {
        SliderAdapter(this, dataSlider)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            initAction()
            initObserver()

            vpSlider.adapter = slider
            TabLayoutMediator(tlHome, vpSlider) { _, _ -> }.attach()

            ivFav.onClick { startActivity(FavoriteActivity::class.java) }
        }
    }

    private fun initAction() {
        viewModel.getHome()
    }

    private fun initObserver() {
        with(binding) {
            viewModel.genre.observe(this@MainActivity) {
                when (it) {
                    is State.Loading -> {
                        toast("Loading")
                    }
                    is State.Success -> {
                        MainScope().launch {
                            it.data.forEach { data ->
                                val view = CategoriesView(applicationContext)
                                    .setHeader(data.toGenre())
                                    .initData { genre ->
                                        if (genre != null) {
                                            viewModel.getGenre(genre.id)
                                        }
                                    }
                                containerContent.addView(view)
                                delay(200)
                            }
                        }
                    }
                    is State.Failure -> {
                        toast("Fail ${it.message}")
                        it.throwable?.printStackTrace()
                    }
                }
            }
            viewModel.discover.observe(this@MainActivity) {
                when (it) {
                    is State.Loading -> msvContent.showLoadingLayout()
                    is State.Success -> {
                        dataSlider.clear()
                        dataSlider.addAll(it.data)
                        msvContent.showDefaultLayout()
                        slider.notifyDataSetChanged()
                    }
                    is State.Failure -> {
                        toast("Fail ${it.message}")
                        it.throwable?.printStackTrace()
                    }
                }
            }
            viewModel.movie.observe(this@MainActivity) {
                when (it) {
                    is State.Success -> {
                        containerContent.children.findLast { view ->
                            view.tag == it.data.first.toString()
                        }?.let { view ->
                            (view as CategoriesView).submitData(it.data.second) { data ->
                                startActivity(DetailActivity::class.java) {
                                    it.putExtra("data", data)
                                }
                            }
                        }
                    }
                    is State.Failure -> {
                        it.throwable?.printStackTrace()
                        Log.e("debug", "debug: ${it.message}")
                    }
                }
            }
        }
    }

    private val dataSlider: MutableList<DataDiscover> by lazy {
        ArrayList()
    }

    private val binding: ActivityMainBinding by viewBinding()
    private val viewModel: MainViewModel by viewModel()
}