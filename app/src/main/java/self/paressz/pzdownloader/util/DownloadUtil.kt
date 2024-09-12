package self.paressz.pzdownloader.util

import android.content.Context
import com.ketch.DownloadConfig
import com.ketch.Ketch
import com.ketch.NotificationConfig
import com.ketch.Status
import self.paressz.pzdownloader.R

fun showDownloadSuccessOrFailed(status: Status, context: Context) {
    if (status == Status.SUCCESS) ToastUtil.showToast(context, context.getString(R.string.download_success))
    else if (status == Status.FAILED) ToastUtil.showToast(context, context.getString(R.string.download_failed))
}

fun getKetch(): Ketch.Companion.Builder {
    return Ketch.builder().setDownloadConfig(
            DownloadConfig(
                connectTimeOutInMs = 15000, readTimeOutInMs = 15000
            )
        ).setNotificationConfig(
            NotificationConfig(
                enabled = true,
                smallIcon = R.drawable.ic_launcher_foreground,
                showSpeed = true,
                showSize = true
            )
        ).enableLogs(true)
}
