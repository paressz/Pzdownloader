package self.paressz.pzdownloader.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import self.paressz.pzdownloader.R
import self.paressz.pzdownloader.databinding.ActivityMainBinding
import self.paressz.pzdownloader.model.MainItem
import self.paressz.pzdownloader.model.MainType
import self.paressz.pzdownloader.ui.BaseActivity

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
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
    }

    fun setupRv() {
        val items = listOf(
            MainItem(R.drawable.logo_facebook_outline_svgrepo_com, "Facebook",  getString(R.string.download_facebook), MainType.FACEBOOK),
            MainItem(R.drawable.logo_instagram_outline_svgrepo_com, "Instagram",  getString(R.string.download_instagram), MainType.INSTAGRAM),
            MainItem(R.drawable.logo_tiktok_svgrepo_com, "TikTok",  getString(R.string.download_tiktok), MainType.TIKTOK),
            MainItem(R.drawable.logo_x, "X",  getString(R.string.download_x), MainType.X),
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
}