package self.paressz.pzdownloader

import android.app.Application
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp
import self.paressz.pzdownloader.databinding.ActivityMainBinding

@HiltAndroidApp
class PzDownloaderApplication : Application() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate() {
        super.onCreate()
    }

    

}