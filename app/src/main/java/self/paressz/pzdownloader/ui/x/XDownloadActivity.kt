package self.paressz.pzdownloader.ui.x

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import self.paressz.core.util.LoadState
import self.paressz.pzdownloader.R
import self.paressz.pzdownloader.databinding.ActivityXdownloadBinding

@AndroidEntryPoint
class XDownloadActivity : AppCompatActivity() {
    private val viewModel : XDownloadViewModel by viewModels()
    private lateinit var binding : ActivityXdownloadBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityXdownloadBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnDownload.setOnClickListener {
            val url = binding.downloadUrl.text.toString()
            downloadVideo(url)
//            lifecycleScope.launch {
//                val x = viewModel.downloadVideo("https://dl.snapcdn.app/get?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1cmwiOiJodHRwczovL3ZpZGVvLnR3aW1nLmNvbS9leHRfdHdfdmlkZW8vMTgyOTE5NzUzMjUxOTY0MTA5MC9wdS92aWQvYXZjMS80ODB4ODAwLzVBTDR0UFV6SVEtell4OUcubXA0P3RhZz0xMiIsImZpbGVuYW1lIjoiU2F2ZVR3aXR0ZXIuTmV0XzVBTDR0UFV6SVEtell4OUdfKDgwMHApLm1wNCIsIm5iZiI6MTcyNTAwMTM5MiwiZXhwIjoxNzI1MDA0OTkyLCJpYXQiOjE3MjUwMDEzOTJ9.drxXpC0DlRB8Ht9g0hm85i6x1DGWefhHPUeYW96Jgrk")
//                Log.d("TAG", "onCreate: $x")
//            }
        }

    }
    fun downloadVideo(url : String) {
        viewModel.downloadX(url).observe(this) { state ->
            when (state) {
                is LoadState.Loading -> {

                }
                is LoadState.Success -> {
                    lifecycleScope.launch {
                        val inputStream = viewModel.downloadVideo(state.data.media[0].url)
                        Log.d("ASGFHJ", "downloadVideo: ${inputStream.readBytes()}")
                    }
                }
                is LoadState.Error -> {
                    Log.d("LOAD ERROR", "downloadVideo: ${state.message}")
                }
            }
        }
    }
}