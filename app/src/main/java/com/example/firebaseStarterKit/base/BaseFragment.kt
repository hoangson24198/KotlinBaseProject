package com.atmaneuler.hsdps.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.atmaneuler.hsdps.helper.KeyboardHelper
import com.example.firebaseStarterKit.view.LayoutResId

abstract class BaseFragment : Fragment() {
    protected var rootView: View? = null
    var alertFromActivity: GeneralAlert? = null
        set(value) {
            field = value
        }

    val alert: GeneralAlert by lazy {
        GeneralAlert(this@BaseFragment.requireActivity())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        alertFromActivity?.progressDismiss()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        processOnSync()
        this@BaseFragment.alert.bind(this@BaseFragment.lifecycle)
        alert.progressDismiss()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutResId: Int = getLayout()
        if (layoutResId != LayoutResId.LAYOUT_NOT_DEFINED) {
            rootView = inflater.inflate(layoutResId, container, false)
        }
        return rootView
    }

    protected open fun getLayout(): Int {
        return if (javaClass.getAnnotation(LayoutResId::class.java) != null) javaClass.getAnnotation(
            LayoutResId::class.java
        )!!.layout else LayoutResId.LAYOUT_NOT_DEFINED
    }

    protected fun processOnSync() {
        onSyncViews()
        onSyncEvents()
        onSyncData()
    }

    protected abstract fun onSyncViews()

    protected abstract fun onSyncEvents()

    protected abstract fun onSyncData()

    protected open fun hideKeyboardIfNeed() {
        KeyboardHelper.hideKeyboardIfNeed(requireActivity())
    }

    override fun onPause() {
        super.onPause()
        hideKeyboardIfNeed()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        rootView = null
    }

}