package self.paressz.pzdownloader.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import self.paressz.pzdownloader.R
import self.paressz.pzdownloader.databinding.ActivityMainBinding
import self.paressz.pzdownloader.ui.fb.FbDownloadActivity
import self.paressz.pzdownloader.ui.ig.IgDownloadActivity
import self.paressz.pzdownloader.ui.x.XDownloadActivity

class MainActivity : AppCompatActivity(), OnClickListener {
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
        binding.xButton.setOnClickListener(this)
        binding.igButton.setOnClickListener(this)
        binding.fbButton.setOnClickListener(this)
        requestPermission()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            binding.xButton.id -> {
                Intent(this, XDownloadActivity::class.java).also { startActivity(it) }
            }

            binding.igButton.id -> {
                Intent(this, IgDownloadActivity::class.java).also { startActivity(it) }
            }

            binding.fbButton.id -> {
                Intent(this, FbDownloadActivity::class.java).also { startActivity(it) }
            }
        }
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