package com.nicolas.imagegallery.ui.activity

import android.app.Activity
import android.content.Intent
import androidx.core.app.ActivityCompat
import com.nicolas.imagegallery.R
import com.nicolas.imagegallery.domain.Picture
import com.nicolas.imagegallery.extensions.shortToast
import com.nicolas.imagegallery.ui.fragment.CommonListFragment

class CommonListActivity: AbstractAppCompatActivity(), CommonListFragment.Callback {

    companion object {
        fun launchActivity(activity: Activity) {
            val intent = Intent(activity, CommonListActivity::class.java)
            ActivityCompat.startActivity(activity, intent, null)
        }
    }

    override fun getBaseLayoutResId() = R.layout.activity_single_fragment

    override fun setInitialFragment() {
        setInitialFragment(CommonListFragment.newInstance())
    }

    override fun onPictureSelected(picture: Picture) {

        ImageViewerActivity.launchActivity(this)
    }
}