package com.nicolas.imagegallery.viewmodel

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.nicolas.imagegallery.R
import com.nicolas.imagegallery.ui.view.LoadingView
import com.nicolas.imagegallery.viewmodel.action.RestHttpAction
import com.nicolas.imagegallery.viewmodel.event.Event
import com.nicolas.imagegallery.viewmodel.event.EventObserver

object ViewModelErrorProvider {

    fun handleError(lifecycleOwner: LifecycleOwner,
                    context: Context,
                    loadingView: LoadingView? = null,
                    onPreExecute: (() -> Unit)? = null
    ): MutableLiveData<Event<RestHttpAction>> {

        val viewStateData: MutableLiveData<Event<RestHttpAction>> = MutableLiveData()

        viewStateData.observe(lifecycleOwner, EventObserver {
            onPreExecute?.invoke()

            when (it) {
                is RestHttpAction.UnknownError -> {
                    if (loadingView != null)
                        loadingView.showErrorView(R.string.connection_error)
                    else
                        Toast.makeText(context, context.getString(R.string.connection_error), Toast.LENGTH_SHORT).show()
                }
                is RestHttpAction.HttpErrorCode -> {
                    var message = it.message
                    if (TextUtils.isEmpty(message)) message = context.getString(R.string.connection_error_try_again)

                    when {
                        loadingView != null -> loadingView.showErrorView(message)
                        else -> Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
                is RestHttpAction.HostUnreachable -> {
                    if (loadingView != null)
                        loadingView.showErrorView(R.string.no_connection)
                    else
                        Toast.makeText(context, R.string.no_connection, Toast.LENGTH_SHORT).show()
                }
            }
        })
        return viewStateData
    }

}