package self.paressz.pzdownloader.ui

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import self.paressz.pzdownloader.R
import self.paressz.pzdownloader.prefs.DownloadLocationPreference

abstract class BaseActivity : AppCompatActivity() {
    lateinit var downloadPref : DownloadLocationPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        useMaterialDynamicThemeForSAndAbove()
        super.onCreate(savedInstanceState)
        downloadPref = DownloadLocationPreference(this)
    }

    private fun useMaterialDynamicThemeForSAndAbove() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setTheme(R.style.MaterialDynamic_Theme)
        }
    }
    fun getTextFromClipboard(): String {
        val cb = getSystemService(CLIPBOARD_SERVICE) as android.content.ClipboardManager
        if(cb.hasPrimaryClip()) {
            val clip = cb.primaryClip!!
            val clipText = clip.getItemAt(0).text.toString()
            return clipText.ifBlank { "" }
        }
        return ""
    }
}