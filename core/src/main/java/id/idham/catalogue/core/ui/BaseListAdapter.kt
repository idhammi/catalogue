package id.idham.catalogue.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseListAdapter(
    @IdRes val layoutId: Int,
    inline val bind: (item: Equatable, view: View) -> Unit
) : ListAdapter<Equatable, BaseListAdapter.ViewHolder>(BaseItemCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bind(getItem(position), holder.itemView)
    }

    override fun getItemViewType(position: Int) = layoutId

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return ViewHolder(root as ViewGroup)
    }

    override fun getItemCount() = currentList.size

    class ViewHolder(container: ViewGroup) : RecyclerView.ViewHolder(container)
}