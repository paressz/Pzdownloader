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
import self.paressz.pzdownloader.ui.BaseActivity
import self.paressz.pzdownloader.util.ToastUtil
import self.paressz.pzdownloader.util.checkIsUrlBlank
import self.paressz.pzdownloader.util.createFileName
import self.paressz.pzdownloader.util.getKetch
import self.paressz.pzdownloader.util.showDownloadSuccessOrFailed
import self.paressz.pzdownloader.util.showErrorMesssage
import self.paressz.pzdownloader.util.showLoading
import java.text.SimpleDateFormat

@AndroidEntryPoint
class XDownloadActivity : BaseActivity() {
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
            hideKeyboard()
            showErrorMesssage(binding.tvError, false)
            val url = binding.etUrl.text.toString()
            val isUrlBlank = checkIsUrlBlank(url)
            if(!isUrlBlank) {
                downloadVideo(url)
            }
        }
    }

    private fun downloadVideo(url: String) {
        viewModel.downloadX(url).observe(this) { state ->
            when (state) {
                is LoadState.Loading -> {
                    showLoading(binding.progressBar,true)
                }

                is LoadState.Success -> {
                    showLoading(binding.progressBar, false)
                    lifecycleScope.launch {
                        if (state.data.media != null) {
                            val videoUrl = state.data.media!!.get(0).url
                            val fileName = createFileName("X", videoUrl)
                            ketchDownload(videoUrl, fileName)
                        } else {
                            ToastUtil.showToast(
                                this@XDownloadActivity,
                                getString(R.string.invalid_url)
                            )
                        }
                    }
                }

                is LoadState.Error -> {
                    showLoading(binding.progressBar, false)
                    showErrorMesssage(binding.tvError, true, state.message)
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
            ketch.observeDownloadById(it).collect { dl ->
                showDownloadSuccessOrFailed(dl.status, this@XDownloadActivity)
                showLoading(binding.progressBar,false)
            }
        }
    }
    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.main.windowToken, 0)
    }
}