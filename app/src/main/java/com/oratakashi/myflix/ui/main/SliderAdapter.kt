package com.oratakashi.myflix.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.oratakashi.myflix.data.model.discover.DataDiscover

class SliderAdapter(
    private val activity: FragmentActivity,
    private val data : List<DataDiscover>
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return data.size
    }

    override fun createFragment(position: Int): Fragment {
        return SliderFragment(data[position])
    }
}