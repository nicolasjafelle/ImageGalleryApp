package com.nicolas.imagegallery.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * Created by nicolas on 11/8/17.
 */
abstract class AbstractFragment<T> : Fragment() {

    protected var callback: T? = null

    protected abstract fun getMainLayoutResId(): Int

    protected abstract fun createViewModel()

    protected abstract fun initUI(view: View)

    protected abstract fun setLiveDataObservers()

    protected abstract fun setListeners()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            callback = context as T
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement Callback interface")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createViewModel()
        setLiveDataObservers()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getMainLayoutResId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(view)
        setListeners()
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

}