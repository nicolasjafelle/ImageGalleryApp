package com.nicolas.imagegallery.ui.view


import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class PagingRecyclerView : RecyclerView {

    var listener: OnLoadMoreListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)


    fun handleScroll( onLoadMore : () -> Unit) {
        this.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1)) {
                    Log.i("PagingRecyclerView", "ITEM COUNT IN ADAPTER = " + adapter!!.itemCount)
                    if (adapter!!.itemCount > 1) {
                        Log.i("PagingRecyclerView", "CANNOT SCROLL VERTICALLY ANYMORE DISPATCH ON LOAD MORE ITEMS")
                        stopScroll()
                        scrollToPosition(adapter!!.itemCount + 1)
                        onLoadMore()
                    }
                }

            }
        })
    }

    fun init(listener: OnLoadMoreListener?) {
        this.listener = listener
        this.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1)) {
                    Log.i("PagingRecyclerView", "ITEM COUNT IN ADAPTER = " + adapter!!.itemCount)
                    if (this@PagingRecyclerView.listener != null && adapter!!.itemCount > 1) {
                        Log.i("PagingRecyclerView", "CANNOT SCROLL VERTICALLY ANYMORE DISPATCH ON LOAD MORE ITEMS")
                        stopScroll()
                        scrollToPosition(adapter!!.itemCount)
                        this@PagingRecyclerView.listener?.onLoadMoreItems()
                    }
                }

            }
        })
    }

    interface OnLoadMoreListener {
        fun onLoadMoreItems()
    }
}