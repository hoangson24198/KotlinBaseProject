@file:Suppress("MemberVisibilityCanBePrivate")

package com.atmaneuler.hsdps.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import timber.log.Timber

interface DPSLifecycleBehaviour : LifecycleObserver {

    var lifecycle: Lifecycle?


    fun bind(lifecycle: Lifecycle?) {
        if (this@DPSLifecycleBehaviour.lifecycle != null) {
            return
        }

        this@DPSLifecycleBehaviour.lifecycle = lifecycle
        this@DPSLifecycleBehaviour.lifecycle?.addObserver(this@DPSLifecycleBehaviour)
    }

    fun unbindIfNeeded() {
        this@DPSLifecycleBehaviour.lifecycle?.removeObserver(this@DPSLifecycleBehaviour)
        this@DPSLifecycleBehaviour.lifecycle = null
    }


    fun lifecycleOnCreate() {
        Timber.i("${this@DPSLifecycleBehaviour::class.java.simpleName} onCreate")
    }

    fun lifecycleOnStart() {
        Timber.i("${this@DPSLifecycleBehaviour::class.java.simpleName} onStart")
    }

    fun lifecycleOnResume() {
        Timber.i("${this@DPSLifecycleBehaviour::class.java.simpleName} onResume")
    }

    fun lifecycleOnPause() {
        Timber.i("${this@DPSLifecycleBehaviour::class.java.simpleName} onPause")
    }

    fun lifecycleOnStop() {
        Timber.i("${this@DPSLifecycleBehaviour::class.java.simpleName} onStop")
    }

    fun lifecycleOnDestroy() {
        this@DPSLifecycleBehaviour.unbindIfNeeded()
        Timber.i("${this@DPSLifecycleBehaviour::class.java.simpleName} onDestroy")
    }

}
