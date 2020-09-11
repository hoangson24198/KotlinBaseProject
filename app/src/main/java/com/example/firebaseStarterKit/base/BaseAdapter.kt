package com.example.firebaseStarterKit.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T>(private var items: ArrayList<T> = arrayListOf()) :
    RecyclerView.Adapter<BaseAdapter.VH>() {
    protected var listener: OnViewHolderListener<T>? = null
    private var filter = ArrayFilter()
    private var listItemsFilter : ArrayList<T> = arrayListOf()
    private var adapterFilter = DefaultFilter<T>()
    private val lock = Any()
    protected abstract fun getLayout(viewType: Int = 0): Int
    protected abstract fun bindView(item: T, viewHolder: VH, position: Int)

    //protected open fun getDiffCallback(): DiffCallback<T>? = null
    private var onFilterObjectCallback: OnFilterObjectCallback? = null
    private var constraint: CharSequence? = ""

    init {
        this.setFilter(adapterFilter)
    }

    override fun onBindViewHolder(vh: VH, position: Int) {
        getItem(position)?.let { bindView(it, vh, position) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        var view = LayoutInflater.from(parent.context).inflate(getLayout(viewType), parent, false)
        val lp = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.setLayoutParams(lp)
        return VH(view)
    }

    override fun getItemCount(): Int = items.size

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    interface OnViewHolderListener<T> {
        fun onItemClick(position: Int, item: T)
    }

    fun getItem(position: Int): T? {
        return items.getOrNull(position)
    }

    fun getItems(): ArrayList<T> {
        return items
    }

    fun setViewHolderListener(listener: OnViewHolderListener<T>) {
        this.listener = listener
    }

    fun addAll(list: List<T>, useDiffUtils: Boolean = true) {
        items.addAll(list)
        //notifyDataSetChanged()
        notifyItemRangeChanged(0, items.size)
    }

    fun update(index: Int, item: T) {
        items.set(index, item)
        //notifyItemChanged(index)
        notifyItemRangeChanged(0, items.size)
    }

    fun add(item: T) {
        //Log.i(SimpleAbstractAdapter::class.java.simpleName, "add: position: item:$item");
        try {
            items.add(0, item)
            //notifyItemInserted(0)
            notifyItemRangeChanged(0, items.size)
        } catch (e: Exception) {
        }
    }

    fun add(position: Int, item: T) {
        //Log.i(SimpleAbstractAdapter::class.java.simpleName, "add: position:$position,item:$item");
        try {
            items.add(position, item)
            notifyItemInserted(position)
        } catch (e: Exception) {
        }
    }

    fun remove(position: Int) {
        //Log.i(SimpleAbstractAdapter::class.java.simpleName, "remove: position:$position");
        try {
            items.removeAt(position)
            notifyItemRemoved(position)
        } catch (e: Exception) {
        }
    }

    fun remove(item: T) {
        //Log.i(SimpleAbstractAdapter::class.java.simpleName, "remove: item:$item");
        try {
            items.remove(item)
            notifyDataSetChanged()
        } catch (e: Exception) {
        }
    }

    fun clear(notify: Boolean = false) {
        //Log.i(SimpleAbstractAdapter::class.java.simpleName, "clear: notify:$notify");
        items.clear()
        if (notify) {
            notifyDataSetChanged()
        }
    }

    fun setFilter(filter: SimpleAdapterFilter<T>): ArrayFilter {
        return this.filter.setFilter(filter)
    }

    interface SimpleAdapterFilter<T> {
        fun onFilterItem(contains: CharSequence, item: T): Boolean
    }

    fun convertResultToString(resultValue: Any): CharSequence {
        return filter.convertResultToString(resultValue)
    }

    fun filter(constraint: CharSequence) {
        this.constraint = constraint
        filter.filter(constraint)
    }

    fun filter(constraint: CharSequence, listener: Filter.FilterListener) {
        this.constraint = constraint
        filter.filter(constraint, listener)
    }

    protected fun itemToString(item: T): String? {
        return item.toString()
    }

    interface OnFilterObjectCallback {
        fun handle(countFilterObject: Int)
    }

    fun setOnFilterObjectCallback(objectCallback: OnFilterObjectCallback) {
        onFilterObjectCallback = objectCallback
    }

    inner class ArrayFilter : Filter() {
        private var original: ArrayList<T> = arrayListOf()
        private var filter: SimpleAdapterFilter<T> = DefaultFilter()
        private var list: ArrayList<T> = arrayListOf()
        private var values: ArrayList<T> = arrayListOf()


        fun setFilter(filter: SimpleAdapterFilter<T>): ArrayFilter {
            original = items
            this.filter = filter
            return this
        }

        override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
            val results = Filter.FilterResults()
            if (constraint == null || constraint.isBlank()) {
                synchronized(lock) {
                    list = original
                }
                results.values = list
                results.count = list.size
            } else {
                synchronized(lock) {
                    values = original
                }
                val result = ArrayList<T>()
                for (value in values) {
                    if (!constraint.isNullOrBlank() && value != null) {
                        if (filter.onFilterItem(constraint, value)) {
                            result.add(value)
                        }
                    } else {
                        value?.let { result.add(it) }
                    }
                }
                results.values = result
                results.count = result.size
            }
            return results
        }

        override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
            items = results.values as? ArrayList<T> ?: arrayListOf()
            notifyDataSetChanged()
            onFilterObjectCallback?.handle(results.count)
        }

    }

    class DefaultFilter<T> : SimpleAdapterFilter<T> {
        override fun onFilterItem(contains: CharSequence, item: T): Boolean {
            val valueText = item.toString().toLowerCase()
            if (valueText.startsWith(contains.toString())) {
                return true
            } else {
                val words =
                    valueText.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                for (word in words) {
                    if (word.contains(contains)) {
                        return true
                    }
                }
            }
            return false
        }
    }
}