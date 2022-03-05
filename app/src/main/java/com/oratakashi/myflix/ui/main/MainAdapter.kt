package com.oratakashi.myflix.ui.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.oratakashi.myflix.BuildConfig
import com.oratakashi.myflix.R
import com.oratakashi.myflix.data.model.discover.DataDiscover
import com.oratakashi.myflix.databinding.AdapterHomeBinding
import com.oratakashi.viewbinding.core.binding.recyclerview.ViewHolder
import com.oratakashi.viewbinding.core.binding.recyclerview.viewBinding
import com.oratakashi.viewbinding.core.tools.loadImage
import com.oratakashi.viewbinding.core.tools.onClick

class MainAdapter(
    private var onClick: ((DataDiscover) -> Unit)? = null
) : RecyclerView.Adapter<ViewHolder<AdapterHomeBinding>>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<AdapterHomeBinding> = viewBinding(parent)

    override fun onBindViewHolder(holder: ViewHolder<AdapterHomeBinding>, position: Int) {
        with(holder.binding) {
//            ivImage.load(BuildConfig.IMAGE_URL + data[position].poster_path) {
//                crossfade(true)
//                placeholder(R.drawable.placeholder_portrait)
//            }
            ivImage.loadImage(
                root.context,
                imageUrl = BuildConfig.IMAGE_URL + data[position].poster_path,
                placeHolderResourceId = R.drawable.placeholder_portrait
            )
            root.onClick { onClick?.invoke(data[position]) }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun submitData(data: List<DataDiscover>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun setAction(action: ((DataDiscover) -> Unit)?) {
        onClick = action
    }

    private val data: MutableList<DataDiscover> by lazy {
        ArrayList()
    }
}