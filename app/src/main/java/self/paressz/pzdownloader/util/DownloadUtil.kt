package self.paressz.pzdownloader.util

import android.content.Context
import com.ketch.DownloadConfig
import com.ketch.Ketch
import com.ketch.NotificationConfig
import com.ketch.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import self.paressz.pzdownloader.R
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Locale


fun createFileName(platform : String, url : String, timeLong: Long = System.currentTimeMillis()) : String {
    var filetype = ""
    CoroutineScope(Dispatchers.IO).launch {
        filetype = guessFileType(url)
    }
    Thread.sleep(750)
    val date = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(timeLong)
    val sb = StringBuilder("PzDl")
    sb.append("_$platform")
    sb.append("_$date")
    //sb.append(Math.random())
    sb.append(".$filetype")
    return sb.toString()
}
fun guessFileType(urlString : String): String {
    val url = URL(urlString)
    val c = url.openConnection() as HttpURLConnection
    c.requestMethod  = "GET"
    c.connect()
    val type = c.contentType
    c.disconnect()
    when (type) {
        "image/jpeg" -> return "jpg"
        "video/mp4" -> return "mp4"
        "image/jpg" -> return "jpg"
        "image/png" -> return "png"
    }
    return ""
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
