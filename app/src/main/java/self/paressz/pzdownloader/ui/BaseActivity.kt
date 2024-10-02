package self.paressz.pzdownloader.ui

import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import self.paressz.pzdownloader.R

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        useMaterialDynamicThemeForSAndAbove()
        super.onCreate(savedInstanceState)
    }
    private fun useMaterialDynamicThemeForSAndAbove() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setTheme(R.style.MaterialDynamic_Theme)
        }
    }
}