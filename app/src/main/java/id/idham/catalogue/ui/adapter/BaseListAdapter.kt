package id.idham.catalogue.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseListAdapter(
    @IdRes val layoutId: Int,
    inline val bind: (item: Equatable, holder: BaseViewHolder, itemCount: Int) -> Unit
) : ListAdapter<Equatable, BaseViewHolder>(BaseItemCallback()) {

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        bind(getItem(position), holder, itemCount)
    }

    override fun getItemViewType(position: Int) = layoutId

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return BaseViewHolder(root as ViewGroup)
    }

    override fun getItemCount() = currentList.size
}

class BaseViewHolder(container: ViewGroup) : RecyclerView.ViewHolder(container)