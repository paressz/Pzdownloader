package self.paressz.pzdownloader.updater

import android.content.Context
import self.paressz.core.constants.PreferenceConstants.UPDATE_KEY
import self.paressz.core.constants.PreferenceConstants.UPDATE_PREFS

class UpdateManager(context: Context) {
    val pref = context.getSharedPreferences( UPDATE_PREFS, Context.MODE_PRIVATE)

    fun getLastCheckTime(): Long = pref.getLong(UPDATE_KEY, 0)

    fun updateLastCheckTime(time : Long) = pref.edit().putLong(UPDATE_KEY, time).apply()

}
