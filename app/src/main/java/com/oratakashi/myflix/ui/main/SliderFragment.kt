package com.oratakashi.myflix.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.oratakashi.myflix.BuildConfig
import com.oratakashi.myflix.R
import com.oratakashi.myflix.data.model.discover.DataDiscover
import com.oratakashi.myflix.databinding.FragmentSliderBinding
import com.oratakashi.myflix.ui.detail.DetailActivity
import com.oratakashi.viewbinding.core.binding.fragment.viewBinding
import com.oratakashi.viewbinding.core.tools.onClick
import com.oratakashi.viewbinding.core.tools.startActivity

class SliderFragment(
    val data: DataDiscover
) : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            root.load(BuildConfig.IMAGE_URL + data.backdrop_path){
                crossfade(true)
                placeholder(R.drawable.placeholder_landscape)
            }
            root.onClick {
                startActivity(DetailActivity::class.java) {
                    it.putExtra("data", data)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }


    private val binding: FragmentSliderBinding by viewBinding()
}