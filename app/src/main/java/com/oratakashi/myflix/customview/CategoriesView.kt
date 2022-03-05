package com.oratakashi.myflix.customview

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.oratakashi.myflix.data.model.discover.DataDiscover
import com.oratakashi.myflix.data.model.genre.DataGenre
import com.oratakashi.myflix.databinding.LayoutCategoriesBinding
import com.oratakashi.myflix.ui.main.MainAdapter
import com.oratakashi.viewbinding.core.binding.customview.viewBinding
import com.oratakashi.viewbinding.core.tools.State
import com.oratakashi.viewbinding.core.tools.showDefaultLayout
import com.oratakashi.viewbinding.core.tools.showLoadingLayout

class CategoriesView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding: LayoutCategoriesBinding by viewBinding()

    private var categories: DataGenre? = null

    private val adapter: MainAdapter by lazy {
        MainAdapter()
    }

    fun setHeader(data: DataGenre): CategoriesView {
        tag = data.id.toString()
        categories = data
        binding.tvHeader.text = data.name
        return this
    }

    fun initData(block: (DataGenre?) -> Unit): CategoriesView {
        block.invoke(categories)
        binding.msvContent.showLoadingLayout()
        return this
    }

    fun submitData(data: List<DataDiscover>, action: (DataDiscover) -> Unit): CategoriesView {
        adapter.setAction(action)
        adapter.submitData(data)
        binding.msvContent.showDefaultLayout()
        return this
    }

    init {
        binding.rvItem.adapter = adapter
        binding.root
    }
}