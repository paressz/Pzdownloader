package self.paressz.pzdownloader.ui.setting

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.util.Log
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import self.paressz.core.extension.toHumanReadable
import self.paressz.pzdownloader.prefs.DownloadLocationPreference
import self.paressz.pzdownloader.R
import self.paressz.pzdownloader.databinding.ActivitySettingBinding
import self.paressz.pzdownloader.model.SettingItem
import self.paressz.pzdownloader.ui.BaseActivity

@AndroidEntryPoint
class SettingActivity : BaseActivity() {
    lateinit var pref : DownloadLocationPreference
    lateinit var binding : ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        pref = DownloadLocationPreference(this)
        setActionBar(binding.toolbar)
        actionBar?.apply {
            title = "Setting"
        }
    }

    override fun onResume() {
        super.onResume()
        setupRv()
    }

    fun setupRv() {
        binding.settingRv.apply {
            layoutManager = LinearLayoutManager(this@SettingActivity)
            adapter = SettingAdapter(getSettingItem())
        }
    }

    fun getSettingItem() : List<SettingItem> {
        val downloadLocation = if (pref.getDownloadLocation() == null) Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path else pref.getDownloadLocation()!!
        return listOf(
            SettingItem(title = "Download Location", description = downloadLocation.toHumanReadable(), onClick = {
                val selectDownloadLocationIntent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
                    if(Build.VERSION.SDK_INT >= 26)
                        putExtra(
                            DocumentsContract.EXTRA_INITIAL_URI,
                            Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_DOWNLOADS
                            ).path
                        )
                }
                selectDownloadLocation.launch(selectDownloadLocationIntent)
            })
        )
    }

    val selectDownloadLocation = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data as Uri
            pref.updateDownloadLocation(uri.path.toString())
        }
    }
}