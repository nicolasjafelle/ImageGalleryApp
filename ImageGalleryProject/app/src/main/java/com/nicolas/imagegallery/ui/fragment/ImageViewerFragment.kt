package com.nicolas.imagegallery.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_image_viewer.*
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader
import com.nicolas.imagegallery.ImageGalleryApplication
import com.nicolas.imagegallery.R
import com.github.piasy.biv.indicator.progresspie.ProgressPieIndicator
import com.github.piasy.biv.loader.ImageLoader
import com.nicolas.imagegallery.domain.PictureDetail
import com.nicolas.imagegallery.ui.view.LoadingView
import com.nicolas.imagegallery.viewmodel.ImageDetailViewModel
import com.nicolas.imagegallery.viewmodel.ImageListViewModel
import com.nicolas.imagegallery.viewmodel.ViewModelErrorProvider
import java.io.File
import java.lang.Exception


class ImageViewerFragment: AbstractFragment<ImageViewerFragment.Callback>(), ImageLoader.Callback {

    lateinit var viewModel: ImageDetailViewModel
    lateinit var loadingView: LoadingView

    interface Callback {
        fun onShareImage(pictureDetail: PictureDetail)
    }

    companion object {

        const val IMAGE_POSITION = "image_pos"

        fun newInstance(imagePosition: Int): Fragment {

            val args = Bundle()
            args.putInt(IMAGE_POSITION, imagePosition)

            val f = ImageViewerFragment()
            f.arguments = args
            return f
        }

//        fun newInstance() = ImageViewerFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        BigImageViewer.initialize(GlideImageLoader.with(ImageGalleryApplication.instance))
    }

    override fun getMainLayoutResId() = R.layout.fragment_image_viewer

    override fun createViewModel() {
        viewModel = ViewModelProviders.of(this).get(ImageDetailViewModel::class.java)
    }


    override fun initUI(view: View) {

        loadingView = LoadingView(requireContext())
        loadingView.attach(view as ViewGroup, onRetryAction = {
            fetchData()
        })

        imageViewerView.setImageLoaderCallback(this)

    }

    override fun setLiveDataObservers() {
        viewModel.viewStateData = ViewModelErrorProvider.handleError(viewLifecycleOwner, requireContext(), loadingView)
        viewModel.detailViewModel.observe(viewLifecycleOwner, Observer {

            imageViewerView.showImage(Uri.parse(it.fullUrl))
        })

        if(viewModel.detailViewModel.value == null) {
           fetchData()
        }
    }

    override fun setListeners() {
        //Do nothing...
    }

    private fun fetchData() {
        loadingView.show()
        arguments?.getInt(IMAGE_POSITION)?.let {
            viewModel.getImageDetail(it)
        }
    }

    override fun onFinish() {
        loadingView.dismiss()
    }

    override fun onSuccess(image: File?) {
        imageViewerView.ssiv.setMinimumDpi(50)
        loadingView.dismiss()
    }

    override fun onFail(error: Exception?) {
        loadingView.showErrorView(R.string.connection_error)
    }

    override fun onCacheHit(imageType: Int, image: File?) {

    }

    override fun onCacheMiss(imageType: Int, image: File?) {

    }

    override fun onProgress(progress: Int) {

    }
}