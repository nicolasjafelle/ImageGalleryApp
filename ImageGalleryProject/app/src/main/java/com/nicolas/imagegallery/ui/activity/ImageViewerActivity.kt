package com.nicolas.imagegallery.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.nicolas.imagegallery.R
import com.nicolas.imagegallery.domain.PictureDetail
import com.nicolas.imagegallery.extensions.shortToast
import com.nicolas.imagegallery.repository.AppRepo
import com.nicolas.imagegallery.ui.adapter.ImagePagerAdapter
import com.nicolas.imagegallery.ui.fragment.ImageViewerFragment
import com.nicolas.imagegallery.utils.ActivityLauncher
import kotlinx.android.synthetic.main.activity_pager_fragment.*


class ImageViewerActivity: AbstractAppCompatActivity(), ImageViewerFragment.Callback {


    companion object {

        /**
         * Use #firstScreen to define which screen has precedence.
         */
        fun launchActivity(activity: Activity) {
            val intent = Intent(activity, ImageViewerActivity::class.java)
            ActivityCompat.startActivity(
                activity,
                intent,
                null
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_pager_fragment)
        super.onCreate(savedInstanceState)

    }

    override fun setInitialFragment() {
        imageViewPager.adapter = ImagePagerAdapter(this, supportFragmentManager)
        imageViewPager.currentItem = AppRepo.selectedPosition!!
    }

    override fun initUI() {
        super.initUI()

        showBackNavigationToolbar()
        setToolbarTitle("")
        toolbar?.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent))
    }

    override fun onShareImage(pictureDetail: PictureDetail) {
        ActivityLauncher.launchShareIntent(this, pictureDetail)
    }
}