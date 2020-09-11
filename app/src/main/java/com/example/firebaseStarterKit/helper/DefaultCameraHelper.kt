package com.atmaneuler.hsdps.helper

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import com.example.firebaseStarterKit.helper.DateHelper
import java.text.SimpleDateFormat
import java.util.*

class DefaultCameraHelper(private val fragment: Fragment) {

    private var takePhotoUri: Uri? = null

    fun reset() {
        takePhotoUri = null
    }

    fun startDefaultCamera() {
        try {
            takePhotoUri = createImageUri()
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, takePhotoUri)
            (fragment as? Fragment)?.startActivityForResult(intent, REQUEST_CODE_TAKE_A_PHOTO)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_TAKE_A_PHOTO && resultCode == Activity.RESULT_OK) {
            takePhotoUri?.let {
                (fragment as? OnDefaultCameraCaptureListener)?.onCaptureDefaultCamera(it)
            } ?: run {
                (fragment as? OnDefaultCameraCaptureListener)?.onCaptureDefaultCamera(Uri.EMPTY)
            }
        }
    }

    private fun createImageUri(): Uri? {
        try {
            val contentResolver = fragment.activity?.contentResolver
            val cv = ContentValues()
            val timeStamp = SimpleDateFormat(DateHelper.IMAGE_DATE_FORMAT, Locale.getDefault()).format(Date())
            cv.put(MediaStore.Images.Media.TITLE, timeStamp)
            return contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    companion object {
        const val REQUEST_CODE_TAKE_A_PHOTO = 107
    }

    interface OnDefaultCameraCaptureListener {
        fun onCaptureDefaultCamera(uri: Uri)
    }
}