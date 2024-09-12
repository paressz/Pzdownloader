package self.paressz.pzdownloader.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Creates a formatted filename based on the platform, URL, and current time.
 *
 * The filename format is: "PzDl_<platform>_<timestamp>.<filetype>".
 *
 * @param platform The platform name.
 * @param url The URL to determine the file type.
 * @param timeLong The time in milliseconds since epoch (default is current time).
 * @return The formatted filename as a String.
 */
suspend fun createFileName(platform : String, url : String, timeLong: Long = System.currentTimeMillis()) = withContext(
    Dispatchers.IO) {
    var filetype = ""
    filetype = guessFileType(url)
    val date = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(timeLong)
    val sb = StringBuilder("PzDl")
    sb.append("_$platform")
    sb.append("_$date")
    sb.append(".$filetype")
    return@withContext sb.toString()
}
/**
 * Guesses the file extension based on the content type from the server response.
 * @param urlString The URL of the resource.
 * @return The guessed file extension as a String, or an empty string if unrecognized.
 */
fun guessFileType(urlString : String): String {
    val url = URL(urlString)
    val c = url.openConnection() as HttpURLConnection
    c.requestMethod  = "GET"
    c.connect()
    val contentType = c.contentType
    c.disconnect()
    when (contentType) {
        "image/jpeg" -> return "jpg"
        "video/mp4" -> return "mp4"
        "image/jpg" -> return "jpg"
        "image/png" -> return "png"
        "application/octet-stream" -> return "mp4"
    }
    return ""
}