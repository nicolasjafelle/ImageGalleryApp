package com.nicolas.imagegallery.ui.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.nicolas.imagegallery.repository.AppRepo
import com.nicolas.imagegallery.ui.fragment.ImageViewerFragment

class ImagePagerAdapter(val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        return ImageViewerFragment.newInstance(position)
    }

    override fun getCount(): Int {
        return AppRepo.pictureList.count()
    }
}