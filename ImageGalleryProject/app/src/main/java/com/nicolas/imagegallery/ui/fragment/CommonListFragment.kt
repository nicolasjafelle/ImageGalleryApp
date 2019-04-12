package com.nicolas.imagegallery.ui.fragment

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.nicolas.imagegallery.R
import com.nicolas.imagegallery.domain.Picture
import com.nicolas.imagegallery.ui.adapter.ImageAdapter
import com.nicolas.imagegallery.ui.view.LoadingView
import com.nicolas.imagegallery.utils.PaddingItemDecorator
import com.nicolas.imagegallery.viewmodel.ImageListViewModel
import com.nicolas.imagegallery.viewmodel.ViewModelErrorProvider
import kotlinx.android.synthetic.main.fragment_common_list.*

class CommonListFragment : AbstractFragment<CommonListFragment.Callback>() {

    lateinit var adapter: ImageAdapter
    lateinit var viewModel: ImageListViewModel
    lateinit var loadingView: LoadingView


    interface Callback {
        fun onPictureSelected(picture: Picture)
    }

    companion object {
        fun newInstance() = CommonListFragment()
    }

    override fun getMainLayoutResId() = R.layout.fragment_common_list

    override fun createViewModel() {
        viewModel = ViewModelProviders.of(this).get(ImageListViewModel::class.java)
    }

    override fun initUI(view: View) {
        loadingView = LoadingView(requireContext())
        loadingView.attach(view as ViewGroup, onRetryAction = {
            fetchData()
        })
        setupRecyclerView()

    }


    override fun setLiveDataObservers() {
        viewModel.viewStateData = ViewModelErrorProvider.handleError(viewLifecycleOwner, requireContext(), loadingView)

        viewModel.listViewModel.observe(viewLifecycleOwner, Observer {
            adapter.addAll(it)
            loadingView.dismiss(true)

//            shortToast("NUMBER OF ITEMS ${adapter.itemCount}")
        })

        if (viewModel.listViewModel.value == null) {
            viewModel.getImageList()
        }

    }

    override fun setListeners() {
        //Do nothing...
        recyclerCommonList.handleScroll {
            viewModel.onLoadMoreItems()
        }
    }

    private fun setupRecyclerView() {

        this.adapter = with(recyclerCommonList) {
            this.setHasFixedSize(true)
            this.isNestedScrollingEnabled = false
            this.layoutManager = GridLayoutManager(requireContext(), resources.getInteger(R.integer.span_count))
            this.itemAnimator = DefaultItemAnimator()
            this.addItemDecoration(PaddingItemDecorator(resources.getInteger(R.integer.span_count), 20))

            return@with ImageAdapter { pos ->

                viewModel.setSelectedPosition(pos)
                val itemSelected = this@CommonListFragment.adapter.getItemAt(pos)

                itemSelected?.let {
                    callback?.onPictureSelected(it)
                }
            }
        }

        recyclerCommonList.adapter = this.adapter

    }

    private fun fetchData() {
        loadingView.show()
        viewModel.getImageList()
    }


}