package self.paressz.core.prefs

import android.content.Context
import android.os.Environment
import self.paressz.core.constants.PreferenceConstants

class DownloadLocationPreference(val context: Context) {
    val pref = context.getSharedPreferences(PreferenceConstants.DOWNLOAD_LOCATION_PREFS, Context.MODE_PRIVATE)

    fun updateDownloadLocation(path: String) {
        if(getDownloadLocation() == "NONE") {
            pref.edit()
                .putString(
                    PreferenceConstants.DOWNLOAD_LOCATION_KEY,
                    Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS
                    ).path
                )
                .apply()
        }
        else pref.edit().putString(PreferenceConstants.DOWNLOAD_LOCATION_KEY, path).apply()
    }

    fun getDownloadLocation() = pref.getString(PreferenceConstants.DOWNLOAD_LOCATION_KEY, "NONE")!!
}