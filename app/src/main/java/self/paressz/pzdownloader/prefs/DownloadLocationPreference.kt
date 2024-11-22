package self.paressz.pzdownloader.prefs

import android.content.Context
import android.os.Environment
import self.paressz.pzdownloader.R
class DownloadLocationPreference(val context: Context) {
    val key = context.getString(R.string.pref_download_location_key)
    val name = context.getString(R.string.pref_download_location)
    val pref = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    fun updateDownloadLocation(path: String) = pref.edit().putString(key, path).apply()

    fun getDownloadLocation() = pref.getString(key, null)
}