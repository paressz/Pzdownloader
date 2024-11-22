package self.paressz.pzdownloader.prefs

import android.content.Context
import self.paressz.pzdownloader.R
class UpdateManager(context: Context) {
    val name = context.getString(R.string.pref_update)
    val key = context.getString(R.string.pref_update_key)
    val pref = context.getSharedPreferences( name, Context.MODE_PRIVATE)

    fun getLastCheckTime(): Long = pref.getLong(key, 0)

    fun updateLastCheckTime(time : Long) = pref.edit().putLong(key, time).apply()

}
