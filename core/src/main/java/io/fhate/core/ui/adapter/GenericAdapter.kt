package io.fhate.core.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.fhate.core.BR
import io.fhate.core.R

/* One-for-all adapter class that works with lists with similar behavior */
class GenericAdapter<T : ListItemViewModel>(
    private val layoutIds: Array<Int>,
    @AnimRes private val animationId: Int? = null
)
    : RecyclerView.Adapter<GenericAdapter.GenericViewHolder<T>>() {

    private val items = mutableListOf<T>()
    private var inflater: LayoutInflater? = null

    private var clickListener: ClickListener? = null

    fun setOnListItemViewClickListener(clickListener: ClickListener?) {
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<T> {
        val layoutInflater = inflater ?: LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, layoutIds[viewType], parent, false)
        return GenericViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: GenericViewHolder<T>, position: Int) {
        val itemViewModel = items[position]
        itemViewModel.adapterPosition = position
        clickListener?.let { itemViewModel.clickListener = it }
        animationId?.let {
            viewHolder.itemView.animation = AnimationUtils.loadAnimation(viewHolder.itemView.context, it)
        }
        viewHolder.bind(itemViewModel)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }

    class GenericViewHolder<T : ListItemViewModel>(private val itemBinding: ViewDataBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: T?) {
            if (item == null) {
                return
            }
            itemBinding.setVariable(BR.item, item)
            //itemBinding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItems() = items

    @SuppressLint("NotifyDataSetChanged")
    fun update(items: List<T>?) {
        this.items.clear()
        this.items.addAll(items?: listOf())
        notifyDataSetChanged()
    }

    fun update(items: List<T>?, diffCallback: DiffUtil.Callback) {
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.items.clear()
        this.items.addAll(items?: listOf())
        diffResult.dispatchUpdatesTo(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun remove(item: ListItemViewModel) {
        this.items.remove(item)
        notifyDataSetChanged()
    }

    fun remove(position: Int) {
        this.items.removeAt(position)
        notifyItemRemoved(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        this.items.clear()
        notifyDataSetChanged()
    }

    fun getItem(position: Int): T {
        return items[position]
    }

    interface ClickListener {
        fun onClick(view: View, position: Int) {}
        fun onLongClick(view: View, position: Int) {}
    }

}
