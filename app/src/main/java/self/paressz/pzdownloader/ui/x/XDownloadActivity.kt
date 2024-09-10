package self.paressz.pzdownloader.ui.x

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
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
import com.ketch.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import self.paressz.core.repository.LoadState
import self.paressz.pzdownloader.R
import self.paressz.pzdownloader.databinding.ActivityXDownloadBinding
import self.paressz.pzdownloader.util.createFileName
import self.paressz.pzdownloader.util.getKetch
import self.paressz.pzdownloader.util.showDownloadSuccessOrFailed
import self.paressz.pzdownloader.util.showToast
import java.text.SimpleDateFormat

@AndroidEntryPoint
class XDownloadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityXDownloadBinding
    private lateinit var ketch: Ketch
    private val viewModel: XDownloadViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityXDownloadBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        ketch = getKetch().build(this)
        binding.btnDownload.setOnClickListener {
            showErrorMessage(false)
            val url = binding.etUrl.text.toString()
            downloadVideo(url)
            hideKeyboard()
        }
    }

    private fun downloadVideo(url: String) {
        viewModel.downloadX(url).observe(this) { state ->
            when (state) {
                is LoadState.Loading -> {
                    showLoading(true)
                }

                is LoadState.Success -> {
                    showLoading(false)
                    lifecycleScope.launch {
                        if (state.data.media != null) {
                            val videoUrl = state.data.media[0].url
                            val fileName = createFileName("X")
                            ketchDownload(videoUrl, fileName)
                        } else showToast(this@XDownloadActivity, getString(R.string.invalid_url))
                    }
                }

                is LoadState.Error -> {
                    showLoading(false)
                    Toast.makeText(this, "Error : ${state.message}", Toast.LENGTH_SHORT).show()
                    showErrorMessage(true, state.message)
                }
            }
        }
    }

    private suspend fun ketchDownload(downloadUrl: String, fileName: String) {
        ketch.download(
            url = downloadUrl,
            fileName = fileName,
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path
        ).also {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                ketch.observeDownloadById(it).collect { dl ->
                    Log.d("PROGRESS", "downloadVideo: ${dl.progress}")
                    showDownloadSuccessOrFailed(dl.status, this@XDownloadActivity)
                }
            }
        }
    }

    private fun showLoading(isVisible: Boolean) {
        binding.progressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun showErrorMessage(isVisible: Boolean, message: String = "") {
        if (isVisible) {
            binding.tvError.visibility = View.VISIBLE
            binding.tvError.text = message
        } else {
            binding.tvError.visibility = View.GONE
        }
    }
    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.main.windowToken, 0)
    }
}