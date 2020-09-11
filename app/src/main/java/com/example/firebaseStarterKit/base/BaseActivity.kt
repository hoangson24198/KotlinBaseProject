package com.atmaneuler.hsdps.base

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.example.firebaseStarterKit.view.LayoutResId
import io.reactivex.Completable
import io.reactivex.disposables.Disposable

abstract class BaseActivity : AppCompatActivity() {
    private var timerDisposable: Disposable? = null
    private var lastTime: Long = System.currentTimeMillis()
    private var timeout: String? = null
    val alert: GeneralAlert by lazy {
        GeneralAlert(this@BaseActivity)
    }

    protected open fun getLayout(): Int {
        return if (javaClass.getAnnotation(LayoutResId::class.java) != null) javaClass.getAnnotation(
            LayoutResId::class.java
        )!!.layout else LayoutResId.LAYOUT_NOT_DEFINED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this@BaseActivity.alert.bind(this@BaseActivity.lifecycle)

        val layoutResId: Int = getLayout()
        if (layoutResId != LayoutResId.LAYOUT_NOT_DEFINED) {
            setContentView(layoutResId)

            onSyncViews(savedInstanceState)
            onSyncEvents()
            onSyncData()
        }
    }

    override fun onStop() {
        super.onStop()
        timerDisposable?.dispose()
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

    }

    override fun onUserInteraction() {
        super.onUserInteraction()

    }



    protected abstract fun onSyncViews(savedInstanceState: Bundle?)

    protected abstract fun onSyncEvents()

    protected abstract fun onSyncData()

    override fun onBackPressed() {//disable back button
        //super.onBackPressed()
        //return
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    protected open fun getFragmentContainer(): Int {
        return LayoutResId.LAYOUT_NOT_DEFINED
    }

    protected open fun getCurrentFragment(): Fragment? {
        if (getFragmentContainer() != LayoutResId.LAYOUT_NOT_DEFINED) {
            val fragment =
                supportFragmentManager.findFragmentById(getFragmentContainer())
            if (fragment != null && fragment.isVisible) return fragment
        }
        return null
    }

//    open fun replace(fragment: Fragment) {
//        // If current fragment is the same with fragment want to replace then skip
//        ViewCompat.animate(frame_layout)
//            .alpha(1f)
//            .setDuration(100)
//            .withEndAction(null)
//            .start()
//        if (getCurrentFragment() != null && getCurrentFragment()!!.javaClass!!.simpleName == fragment.javaClass.simpleName) {
//            alert.progressDismiss()
//            return
//        }
//
//        try {
//
//
//            supportFragmentManager.beginTransaction()
//                //.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
//                .replace(R.id.frame_layout, fragment)
//                .addToBackStack(fragment.javaClass.simpleName)
//                .commitAllowingStateLoss()
//
//            supportFragmentManager.executePendingTransactions()
//            //alert.progressDismiss()
//        } catch (e: Exception) {
//            //alert.progressDismiss()
//            e.printStackTrace()
//        }
//    }

    fun checkCurrent(fragment: Fragment): Boolean {
        if (getCurrentFragment() != null && getCurrentFragment()?.javaClass?.simpleName == fragment.javaClass.simpleName)
            return true
        return false
    }
}