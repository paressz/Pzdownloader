package self.paressz.pzdownloader.updater

import android.content.Context

class UpdateManager(context: Context) {
    val pref = context.getSharedPreferences( PREFS, Context.MODE_PRIVATE)

    fun getLastCheckTime(): Long = pref.getLong(UPDATE_KEY, 0)

    fun updateLastCheckTime(time : Long) = pref.edit().putLong(UPDATE_KEY, time).apply()

    companion object {
        const val UPDATE_KEY = "UPDATE"
        const val PREFS = "PREFS"
    }
}
