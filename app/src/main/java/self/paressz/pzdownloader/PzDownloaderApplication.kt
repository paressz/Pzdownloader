package self.paressz.pzdownloader

import android.app.Application
import android.os.Environment
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp
import self.paressz.pzdownloader.databinding.ActivityMainBinding
import self.paressz.pzdownloader.prefs.DownloadLocationPreference

@HiltAndroidApp
class PzDownloaderApplication : Application() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate() {
        super.onCreate()
        val pref = DownloadLocationPreference(this)
        if(pref.getDownloadLocation() == null) pref.updateDownloadLocation(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS
            ).path
        )
    }

    

}