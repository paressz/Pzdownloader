package self.paressz.pzdownloader.ui.x

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ketch.DownloadConfig
import com.ketch.Ketch
import com.ketch.NotificationConfig
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import self.paressz.core.repository.LoadState
import self.paressz.pzdownloader.R
import self.paressz.pzdownloader.databinding.ActivityXdownloadBinding

@AndroidEntryPoint
class XDownloadActivity : AppCompatActivity() {
    private val viewModel : XDownloadViewModel by viewModels()
    private lateinit var binding : ActivityXdownloadBinding
    private lateinit var ketch : Ketch
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityXdownloadBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(binding.main.id)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        buildKetch()
        binding.btnDownload.setOnClickListener {
            showErrorMessage(false)
            val url = binding.downloadUrl.text.toString()
            ketch.clearAllDb()
            downloadVideo(url)
        }

    }
    fun downloadVideo(url : String) {
        viewModel.downloadX(url).observe(this) { state ->
            when (state) {
                is LoadState.Loading -> {
                    showLoading(true)
                }
                is LoadState.Success -> {
                    showLoading(false)
                    lifecycleScope.launch {
                        val videoUrl = state.data.media[0].url
                        //val inputStream = viewModel.downloadVideo(videoUrl)
                        ketch.download(
                            url = videoUrl,
                            fileName = "test.mp4",
                            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path
                        ).also {
                            repeatOnLifecycle(Lifecycle.State.STARTED) {
                                ketch.observeDownloadById(it).collect { dl ->
                                    Log.d("PROGRESS", "downloadVideo: ${dl.progress}")
                                }
                            }
                        }
                        //Log.d("ASGFHJ", "downloadVideo: ${inputStream.readBytes()}")
                    }
                }
                is LoadState.Error -> {
                    showLoading(false)
                    Toast.makeText(this, "Error : ${state.message}", Toast.LENGTH_SHORT).show()
                    showErrorMessage(true, state.message)
                    Log.d("LOAD ERROR", "downloadVideo: ${state.message}")
                }
            }
        }
    }
    fun buildKetch() {
        ketch = Ketch.builder()
            .setDownloadConfig(DownloadConfig(
                connectTimeOutInMs = 15000,
                readTimeOutInMs = 15000
            ))
            .setNotificationConfig(NotificationConfig(
                enabled = true,
                smallIcon = R.drawable.ic_launcher_foreground,
                showSpeed = true,
                showSize = true
            ))
            .enableLogs(true)
            .build(this)
    }
    fun showLoading(isVisible : Boolean) {
        binding.progressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
    fun showErrorMessage(isVisible: Boolean, message : String = "") {
        if(isVisible) {
            binding.tvError.visibility = View.VISIBLE
            binding.tvError.text = message
        } else {
            binding.tvError.visibility = View.GONE
        }
    }

}