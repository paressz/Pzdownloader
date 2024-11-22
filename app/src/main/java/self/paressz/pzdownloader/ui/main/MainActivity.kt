package self.paressz.pzdownloader.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import self.paressz.core.repository.LoadState
import self.paressz.pzdownloader.BuildConfig
import self.paressz.pzdownloader.R
import self.paressz.pzdownloader.databinding.ActivityMainBinding
import self.paressz.pzdownloader.model.MainItem
import self.paressz.pzdownloader.model.MainType
import self.paressz.pzdownloader.ui.BaseActivity
import self.paressz.pzdownloader.ui.setting.SettingActivity
import self.paressz.pzdownloader.prefs.UpdateManager
import self.paressz.pzdownloader.util.ToastUtil
import self.paressz.pzdownloader.util.newVersionAvailable
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var updater : UpdateManager
    val viewModel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupRv()
        requestPermission()
        if(isConnectedToInternet()) checkForUpdate()
        else ToastUtil.showToast(this, "APP NEED INTERNET CONNECTION")
        binding.btnSetting.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }
    }

    fun setupRv() {
        val items = listOf(
            MainItem(R.drawable.logo_facebook_outline_svgrepo_com, "Facebook",  getString(R.string.download_facebook), MainType.FACEBOOK),
            MainItem(R.drawable.logo_instagram_outline_svgrepo_com, "Instagram",  getString(R.string.download_instagram), MainType.INSTAGRAM),
            MainItem(R.drawable.logo_tiktok_svgrepo_com, "TikTok",  getString(R.string.download_tiktok), MainType.TIKTOK),
            MainItem(R.drawable.logo_x, "X",  getString(R.string.download_x), MainType.X),
            MainItem(R.drawable.logo_youtube_svgrepo_com, "Youtube", getString(R.string.download_youtube), MainType.YOUTUBE),
        )
        val rv = binding.mainRv
        rv.layoutManager = GridLayoutManager(this, 2)
        rv.adapter = MainAdapter(items, this)
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
            }
        } else {
            val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this, permissions, 2)
            }
        }
    }
    private fun isConnectedToInternet() : Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = cm.activeNetwork
            cm.getNetworkCapabilities(networkCapabilities).let {
                when {
                    it == null -> return false
                    it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) -> return true
                    else -> return false
                }
            }
        } else {
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnected
        }
    }
    private fun showUpdateDialog(versionTag: String, changelog: String, url : String) {
        val dialog = UpdateDialog(this)
        dialog.setTexts(versionTag, changelog)
        dialog.setOnUpdateButtonClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }
        dialog.show()
    }

    private fun checkForUpdate() {
        updater = UpdateManager(this)
        val lastCheck = updater.getLastCheckTime()
        val currentTime = System.currentTimeMillis()
        if(currentTime - lastCheck >= TimeUnit.DAYS.toMillis(1)) {
            Log.d("TIMES", "checkForUpdate: $currentTime $lastCheck ${TimeUnit.DAYS.toMillis(1)}")
            viewModel.checkForUpdate().observe(this@MainActivity) { state ->
                if (state is LoadState.Success) {
                    val releaseData = state.data
                    val latestVersion = releaseData.tagName.removePrefix("v")
                    val currentVersion= BuildConfig.VERSION_NAME
                    updater.updateLastCheckTime(currentTime)
                    if(newVersionAvailable(latestVersion, currentVersion)) {
                        showUpdateDialog(releaseData.tagName, releaseData.body, releaseData.releaseUrl)
                    }
                }
            }
        }
    }
}