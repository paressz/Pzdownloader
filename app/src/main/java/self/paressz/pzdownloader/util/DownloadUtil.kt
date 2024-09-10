package self.paressz.pzdownloader.util

import android.content.Context
import com.ketch.DownloadConfig
import com.ketch.Ketch
import com.ketch.NotificationConfig
import com.ketch.Status
import self.paressz.pzdownloader.R
import java.text.SimpleDateFormat
import java.util.Locale


fun createFileName(platform : String) : String {
    val date = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(System.currentTimeMillis())
    val fileName = "PzDl-$platform-$date.mp4"
    return fileName
}
fun showDownloadSuccessOrFailed(status : Status, context: Context) {
    if (status == Status.SUCCESS) showToast(context, context.getString(R.string.download_success))
    else if (status == Status.FAILED) showToast(context, context.getString(R.string.download_failed))
}
fun getKetch() : Ketch.Companion.Builder {
    return Ketch.builder()
        .setDownloadConfig(
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
