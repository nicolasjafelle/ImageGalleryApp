package com.nicolas.imagegallery.ui.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.annotation.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.nicolas.imagegallery.R

/**
 * Created by nicolas on 11/7/17.
 */
abstract class AbstractAppCompatActivity : AppCompatActivity() {

    protected var toolbar: Toolbar? = null

    protected var mainLayout: FrameLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setInitialFragment()
        setToolbar()
        initUI()
    }

    protected open fun initUI() {

    }

    protected open fun setToolbar() {
        toolbar = findViewById(R.id.toolbar_home)
        setSupportActionBar(toolbar)
    }


    protected open fun showBackNavigationToolbar() {
        showToolbar()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    protected open fun hideToolbar() {
        supportActionBar?.hide()
    }

    protected fun showToolbar() {
        supportActionBar?.show()
    }

    protected fun setToolbarTitle(@StringRes titleResId: Int) {
        supportActionBar?.title = getString(titleResId)
    }

    protected open fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    protected fun setTranslucentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            )
        }
    }

    protected fun getStatusBarHeight(): Int {
        var result = 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = resources.getDimensionPixelSize(resourceId)
            }
        }
        return result
    }

    protected fun setMaterialStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        }
    }

    protected fun setMaterialStatusBarColor(@ColorRes colorResId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

            with(window) {
                this.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                this.statusBarColor = ContextCompat.getColor(this@AbstractAppCompatActivity, colorResId)
            }
        }
    }

    protected fun setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            with(window) {
                this.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                //IN JAVA
//                this.decorView.systemUiVisibility =  View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                this.statusBarColor = Color.TRANSPARENT
            }
        }
    }

    protected fun setToolbarColor(@ColorRes colorRes: Int) {
        toolbar?.setBackgroundResource(colorRes)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    protected abstract fun setInitialFragment()

    protected fun setInitialFragment(
        fragment: Fragment, @LayoutRes layoutResId: Int = getBaseLayoutResId(),
        @IdRes viewId: Int = R.id.fragment_container
    ) {
        setContentView(layoutResId)
        mainLayout = findViewById(viewId)

        setInitialFragment(mainLayout, fragment)
    }


    protected fun setInitialFragment(view: View?, fragment: Fragment) {
        if (getCurrentFragment() == null) {
            val transaction = supportFragmentManager.beginTransaction()
            if (view != null) {
                transaction.add(view.id, fragment, "ROOT").commit()
            }
        }
    }

    protected fun getCurrentFragment(): Fragment? {
        val id = mainLayout!!.id
        return supportFragmentManager.findFragmentById(id)
    }

    protected fun popBackStack() = supportFragmentManager.popBackStackImmediate()

    open protected fun getBaseLayoutResId() = R.layout.activity_scroll_single_fragment

    fun replaceFragment(
        newFragment: Fragment,
        addToBackStack: Boolean = true,
        @AnimRes enterAnim: Int = R.anim.fragment_fade_in,
        @AnimRes exitAnim: Int = 0,
        @AnimRes popEnterAnim: Int = R.anim.fragment_fade_in,
        @AnimRes popExitAnim: Int = 0
    ) {

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (addToBackStack) {
            fragmentTransaction.addToBackStack("replace")
        }

        fragmentTransaction.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        fragmentTransaction.replace(mainLayout!!.id, newFragment).commit()
    }

    @SuppressLint("ResourceType")
    fun addAndHideFragment(
        newFragment: Fragment,
        addToBackStack: Boolean = true,
        @AnimRes enterAnim: Int = R.anim.fragment_fade_in,
        @AnimRes exitAnim: Int = 0,
        @AnimRes popEnterAnim: Int = R.anim.fragment_fade_in,
        @AnimRes popExitAnim: Int = 0
    ) {

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        if (addToBackStack) {
            fragmentTransaction.addToBackStack("add_and_hide")
        }

        if (enterAnim > 0 && exitAnim > 0) {
            fragmentTransaction.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        } else {
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE)
            fragmentTransaction.setCustomAnimations(
                enterAnim,
                exitAnim,
                popEnterAnim,
                popExitAnim
            )
        }

        val currentFragment = getCurrentFragment()

        currentFragment?.let {
            fragmentTransaction.hide(it)
            fragmentTransaction.add(mainLayout!!.id, newFragment).commit()
        }

    }

}


