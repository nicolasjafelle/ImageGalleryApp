package com.nicolas.imagegallery.ui.fragment

import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.nicolas.imagegallery.R
import com.nicolas.imagegallery.viewmodel.AuthViewModel
import com.nicolas.imagegallery.viewmodel.ViewModelErrorProvider
import com.nicolas.imagegallery.viewmodel.event.EventObserver
import kotlinx.android.synthetic.main.fragment_starter.*

class StarterFragment : AbstractFragment<StarterFragment.Callback>() {

    lateinit var viewModel: AuthViewModel


    companion object {
        fun newInstance() = StarterFragment()
    }

    interface Callback {
        fun onContinue()
    }


    override fun getMainLayoutResId() = R.layout.fragment_starter

    override fun createViewModel() {
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)


        viewModel.authenticate()
    }

    override fun initUI(view: View) {
        loadingView.show()


    }

    override fun setLiveDataObservers() {
        viewModel.viewStateData = ViewModelErrorProvider.handleError(viewLifecycleOwner, requireContext(), loadingView)
        viewModel.authLiveData.observe(viewLifecycleOwner, EventObserver {
            val success = viewModel.storeCredentials(requireContext(), it.token)

            if (success) {
                loadingView.dismiss()
                callback?.onContinue()
            }
        })
    }

    override fun setListeners() {
        //Do nothing...
    }
}