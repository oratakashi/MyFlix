package com.oratakashi.myflix.ui.detail

import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import coil.transform.RoundedCornersTransformation
import com.oratakashi.myflix.BuildConfig
import com.oratakashi.myflix.R
import com.oratakashi.myflix.data.model.discover.DataDiscover
import com.oratakashi.myflix.databinding.ActivityDetailBinding
import com.oratakashi.myflix.utils.dateFormat
import com.oratakashi.viewbinding.core.binding.activity.viewBinding
import com.oratakashi.viewbinding.core.binding.intent.intent
import com.oratakashi.viewbinding.core.tools.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            setupToolbar()
            initObserver()
            initAction()
        }
    }

    private fun initObserver() {
        with(binding) {
            viewModel.movie.observe(this@DetailActivity) {
                when(it) {
                    is State.Loading    -> msvContent.showLoadingLayout()
                    is State.Success    -> {
                        msvContent.showDefaultLayout()
                        viewModel.isFav(it.data)
                        ivBackground.load(BuildConfig.IMAGE_URL + it.data.backdrop_path) {
                            crossfade(true)
                            placeholder(R.drawable.placeholder_landscape)
                        }
                        ivImage.load(BuildConfig.IMAGE_URL + it.data.poster_path) {
                            crossfade(true)
                            placeholder(R.drawable.placeholder_portrait)
                            transformations(RoundedCornersTransformation(25f))
                        }
                        tvTitle.text = it.data.title
                        if (it.data.release_date != null && it.data.release_date?.isNotEmpty() == true) {
                            tvDate.dateFormat(it.data.release_date.orEmpty(), "yyyy-MM-dd", "dd MMMM yyyy")
                        } else {
                            tvDate.text = getString(R.string.title_unknown)
                        }
                        tvOverview.text = if (it.data.overview!!.isNotEmpty()) {
                            it.data.overview
                        } else {
                            getString(R.string.title_unknown)
                        }
                        if (it.data.vote_average != null) {
                            rbRatting.rating = ((it.data.vote_average ?: 0f) / 2)
                            tvRatting.text = ((it.data.vote_average ?: 0f) / 2).toString()
                        }

                        ivFavCircle.onClick {
                            viewModel.addFav(it.data)
                        }
                        ivFav.onClick {
                            viewModel.addFav(it.data)
                        }
                    }
                }
            }
            lifecycleScope.launchWhenResumed { 
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.isFav.collect {
                        if(it) {
                            ivFavCircle.load(R.drawable.ic_fav_fill)
                        } else {
                            ivFavCircle.load(R.drawable.ic_favorite)
                        }
                    }
                }
            }
        }
    }

    private fun initAction() {
        viewModel.getDetailMovie(data.id)
    }

    @Suppress("DEPRECATION")
    private fun setupToolbar() {
        with(binding) {
            ivClose.onClick { onBackPressed() }
            ivBack.onClick { onBackPressed() }

            tvToolbar.text = data.title
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                    Configuration.UI_MODE_NIGHT_YES -> {
                        window.decorView.systemUiVisibility =
                            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
                    }
                    Configuration.UI_MODE_NIGHT_NO -> {
                        window.decorView.systemUiVisibility =
                            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                    }
                }
            } else {
                window.setDecorFitsSystemWindows(true)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                nsContent.setOnScrollChangeListener { _, _, scrollY, _, _ ->
                    if (scrollY > 250) {
                        toolbar.visible()
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                                Configuration.UI_MODE_NIGHT_YES -> {
                                    window.decorView.systemUiVisibility =
                                        View.SYSTEM_UI_FLAG_VISIBLE
                                }
                                Configuration.UI_MODE_NIGHT_NO -> {
                                    window.decorView.systemUiVisibility =
                                        (View.SYSTEM_UI_FLAG_VISIBLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                                }
                            }
                        } else {
                            window.setDecorFitsSystemWindows(true)
                        }
                    } else {
                        toolbar.invisible()
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                                Configuration.UI_MODE_NIGHT_YES -> {
                                    window.decorView.systemUiVisibility =
                                        (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
                                }
                                Configuration.UI_MODE_NIGHT_NO -> {
                                    window.decorView.systemUiVisibility =
                                        (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
                                }
                            }
                        } else {
                            window.setDecorFitsSystemWindows(false)
                        }
                    }
                }
            }
        }
    }

    private val binding: ActivityDetailBinding by viewBinding()
    private val viewModel: DetailViewModel by viewModel()
    private val data: DataDiscover by intent("data")
}