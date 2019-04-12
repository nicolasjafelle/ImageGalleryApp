package com.nicolas.imagegallery.ui.activity


import com.nicolas.imagegallery.ImageGalleryApplication
import com.nicolas.imagegallery.ui.fragment.StarterFragment

/**
 * Created by nicolas on 11/7/17.
 */
class StarterActivity : AbstractAppCompatActivity(), StarterFragment.Callback {

    private val application by lazy { ImageGalleryApplication.instance }


    override fun setInitialFragment() {
        if (application.isLogged()) {
            onContinue()
        } else {
            setInitialFragment(StarterFragment.newInstance())
        }
    }

    override fun onContinue() {
        CommonListActivity.launchActivity(this)
        finish()
    }
}
