@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.atmaneuler.hsdps.base

import android.content.Context
import android.os.Looper
import android.widget.Toast
import androidx.annotation.IntRange
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.afollestad.materialdialogs.MaterialDialog
import com.example.firebaseStarterKit.lib.ios.iOSDialogBuilder
import com.example.ui.fragment.onBoarding.walkthroughactivity.R
import com.kaopiz.kprogresshud.KProgressHUD


class GeneralAlert(private val context: Context) : DPSLifecycleBehaviour {

    override var lifecycle: Lifecycle? = null
    private var progressHUD: KProgressHUD? = null
    private var alertDialog: MaterialDialog? = null

    fun toast(message: String, isLongToast: Boolean = false) {
        if (Looper.getMainLooper() != Looper.myLooper()) return

        Toast.makeText(
            this@GeneralAlert.context,
            message,
            if (isLongToast) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        ).show()
    }

    fun toastError(message: String, isLongToast: Boolean = false) {
        if (Looper.getMainLooper() != Looper.myLooper()) return

        var toast = Toast.makeText(
            this@GeneralAlert.context,
            message,
            if (isLongToast) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        )
        toast.view.setBackgroundResource(R.color.colorAccent)
        toast.show()
    }

    /**
     * display short toast
     * @see toast(String, Boolean)
     *
     * @param messageResId Int string resource id need to get message
     * @param isLongToast Boolean true if need to expand toasts duration
     */
    fun toast(@StringRes messageResId: Int, isLongToast: Boolean = false) {
        if (Looper.getMainLooper() != Looper.myLooper()) return

        this@GeneralAlert.toast(this@GeneralAlert.context.getString(messageResId), isLongToast)
    }

    /**
     * display hud progress
     *
     * @param message String bottom text need to display as status
     * @param percent Int? progress need to display, set to null (default) mean show progress as indeterminate
     */
    fun progressShow(message: String, @IntRange(from = 0, to = 100) percent: Int? = null) {
        if (Looper.getMainLooper() != Looper.myLooper()) return

        if (this@GeneralAlert.progressHUD == null) {
            this@GeneralAlert.progressHUD =
                KProgressHUD.create(
                    this@GeneralAlert.context,
                    KProgressHUD.Style.ANNULAR_DETERMINATE
                )//spin
                    .setAnimationSpeed(2)//remove
                    .setCancellable(false)
        }

        if (!message.isNullOrEmpty())
            this@GeneralAlert.progressHUD?.setLabel(message)

        if (percent != null && percent in 0..100) {
            this@GeneralAlert.progressHUD?.setStyle(KProgressHUD.Style.PIE_DETERMINATE)
            this@GeneralAlert.progressHUD?.setProgress(percent)
        } else {
            this@GeneralAlert.progressHUD?.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)//spin
        }

        if (this@GeneralAlert.progressHUD?.isShowing != true) {
            try {
                this@GeneralAlert.progressHUD?.show()
            } catch (exception: Exception) {
            }
        }
    }

    fun progressShowWithDim(message: String, @IntRange(from = 0, to = 100) percent: Int? = null) {
        if (Looper.getMainLooper() != Looper.myLooper()) return

        if (this@GeneralAlert.progressHUD == null) {
            this@GeneralAlert.progressHUD =
                KProgressHUD.create(
                    this@GeneralAlert.context,
                    KProgressHUD.Style.ANNULAR_DETERMINATE
                )//spin
                    .setAnimationSpeed(2)//remove
                    .setDimAmount(0.3f)
                    .setCancellable(false)
        }

        if (!message.isNullOrEmpty())
            this@GeneralAlert.progressHUD?.setLabel(message)

        if (percent != null && percent in 0..100) {
            this@GeneralAlert.progressHUD?.setStyle(KProgressHUD.Style.PIE_DETERMINATE)
            this@GeneralAlert.progressHUD?.setProgress(percent)
        } else {
            this@GeneralAlert.progressHUD?.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)//spin
        }

        if (this@GeneralAlert.progressHUD?.isShowing != true) {
            try {
                this@GeneralAlert.progressHUD?.show()
            } catch (exception: Exception) {
            }
        }
    }

    fun progressShow(
        @StringRes messageResId: Int,
        @IntRange(from = 0, to = 100) percent: Int? = null
    ) {
        if (Looper.getMainLooper() != Looper.myLooper()) return

        this@GeneralAlert.progressShow(this@GeneralAlert.context.getString(messageResId), percent)
    }

    fun progressDismiss() {
        if (Looper.getMainLooper() != Looper.myLooper()) return

        this@GeneralAlert.releaseProgressHUDIfNeeded()
    }

    private fun releaseProgressHUDIfNeeded() {
        if (Looper.getMainLooper() != Looper.myLooper()) return

        this@GeneralAlert.progressHUD?.let { unwrapped ->
            if (unwrapped.isShowing) {
                unwrapped.dismiss()
            }
        }
        this@GeneralAlert.progressHUD = null
    }

    fun alert(
        errorMessage: String,
        closeButtonText: String? = null,
        onClose: (() -> Unit)? = null
    ) {
        if (Looper.getMainLooper() != Looper.myLooper()) return
        this@GeneralAlert.releaseAlertDialogIfNeeded()

        this@GeneralAlert.alertDialog = this@GeneralAlert.getAlertBuilder()
            .content(errorMessage)
            .positiveText(
                closeButtonText
                    ?: this@GeneralAlert.context.getString(android.R.string.ok)
            )
            .build()
        this@GeneralAlert.alertDialog?.setOnDismissListener { _ ->
            onClose?.invoke()
            this@GeneralAlert.releaseAlertDialogIfNeeded()
        }
        this@GeneralAlert.alertDialog?.show()
    }

    fun alert(
        @StringRes messageResId: Int,
        @StringRes closeBtnResId: Int? = null,
        onClose: (() -> Unit)? = null
    ) {
        if (Looper.getMainLooper() != Looper.myLooper()) return
        this@GeneralAlert.alert(
            errorMessage = this@GeneralAlert.context.getString(messageResId),
            closeButtonText = if (closeBtnResId != null) this@GeneralAlert.context.getString(
                closeBtnResId
            ) else null,
            onClose = onClose
        )
    }

    private fun getAlertBuilder(): MaterialDialog.Builder {
        return MaterialDialog.Builder(this@GeneralAlert.context)
            .typeface(
                ResourcesCompat.getFont(context, R.font.cairobold),
                ResourcesCompat.getFont(context, R.font.cairoregular)
            )
            .contentColorRes(R.color.black_overlay)
    }

    fun alertError(message: String) {
        if (Looper.getMainLooper() != Looper.myLooper()) return
        iOSDialogBuilder(this@GeneralAlert.context)
            .setTitle(this@GeneralAlert.context.getString(R.string.label_general_error))
            .setSubtitle(message)
            .setBoldPositiveLabel(true)
            .setCancelable(false)
            .setPositiveListener(
                this@GeneralAlert.context.getString(R.string.label_ok)
            ) { dialog ->
                //Toast.makeText(this@MainActivity, "Clicked!", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }
            /*
        .setNegativeListener(
            this@AtmanAlert.context.getString(R.string.dismiss)
        ) { dialog -> dialog.dismiss() } */
            .build().show()
    }

    fun alertInfo(message: String) {
        if (Looper.getMainLooper() != Looper.myLooper()) return
        iOSDialogBuilder(this@GeneralAlert.context)
            .setTitle(this@GeneralAlert.context.getString(R.string.label_general_error))
            .setSubtitle(message)
            .setBoldPositiveLabel(true)
            .setCancelable(false)
            .setPositiveListener(
                this@GeneralAlert.context.getString(R.string.label_ok)
            ) { dialog ->
                //Toast.makeText(this@MainActivity, "Clicked!", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }
            /*
        .setNegativeListener(
            this@AtmanAlert.context.getString(R.string.dismiss)
        ) { dialog -> dialog.dismiss() } */
            .build().show()
    }

    fun alertSuccess(message: String) {
        if (Looper.getMainLooper() != Looper.myLooper()) return
        iOSDialogBuilder(this@GeneralAlert.context)
            .setTitle(this@GeneralAlert.context.getString(R.string.label_success))
            .setSubtitle(message)
            .setBoldPositiveLabel(true)
            .setCancelable(false)
            .setPositiveListener(
                this@GeneralAlert.context.getString(R.string.label_ok)
            ) { dialog ->
                //Toast.makeText(this@MainActivity, "Clicked!", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }
            /*
        .setNegativeListener(
            this@AtmanAlert.context.getString(R.string.dismiss)
        ) { dialog -> dialog.dismiss() } */
            .build().show()
    }

    fun alertConfirm(message: String, onYes: (() -> Unit)? = null, onNo: (() -> Unit)? = null) {
        if (Looper.getMainLooper() != Looper.myLooper()) return
        iOSDialogBuilder(this@GeneralAlert.context)
            .setTitle(this@GeneralAlert.context.getString(R.string.label_general_error))
            .setSubtitle(message)
            .setBoldPositiveLabel(true)
            .setCancelable(false)
            .setPositiveListener(
                this@GeneralAlert.context.getString(R.string.label_yes)
            ) { dialog ->
                //Toast.makeText(this@MainActivity, "Clicked!", Toast.LENGTH_LONG).show()
                dialog.dismiss()
                onYes?.invoke()
            }

            .setNegativeListener(
                this@GeneralAlert.context.getString(R.string.label_no)
            ) { dialog ->
                dialog.dismiss()
                onNo?.invoke()
            }
            .build().show()
    }

    private fun releaseAlertDialogIfNeeded() {
        if (Looper.getMainLooper() != Looper.myLooper()) return

        this@GeneralAlert.alertDialog?.let { unwrapped ->
            if (unwrapped.isShowing) {
                unwrapped.dismiss()
            }
        }
        this@GeneralAlert.alertDialog = null
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun lifecycleOnDestroy() {
        super.lifecycleOnDestroy()
        this@GeneralAlert.releaseProgressHUDIfNeeded()
    }

}
