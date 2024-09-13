package self.paressz.pzdownloader.ui.ig

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import self.paressz.core.repository.LoadState
import self.paressz.pzdownloader.R
import self.paressz.pzdownloader.databinding.ActivityIgDownloadBinding
import self.paressz.pzdownloader.util.ToastUtil
import self.paressz.pzdownloader.util.checkIsUrlBlank
import self.paressz.pzdownloader.util.createFileName
import self.paressz.pzdownloader.util.getKetch
import self.paressz.pzdownloader.util.showDownloadSuccessOrFailed
import self.paressz.pzdownloader.util.showErrorMesssage

@AndroidEntryPoint
class IgDownloadActivity : AppCompatActivity() {
    private lateinit var binding : ActivityIgDownloadBinding
    private lateinit var ketch: Ketch
    private val viewModel : IgDownloadViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIgDownloadBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        ketch = getKetch().build(this)
        binding.btnDownload.setOnClickListener {
            showErrorMesssage(binding.tvError, false)
            hideKeyboard()
            val url = binding.etUrl.text.toString()
            val isUrlBlank = checkIsUrlBlank(url)
            if(!isUrlBlank) {
                downloadVideo(url)
            }
        }
    }
    fun downloadVideo(url:String) {
            viewModel.getDownloadUrl(url).observe(this@IgDownloadActivity) { state ->
                when (state) {
                    is LoadState.Loading -> {
                        showLoading(true)
                    }
                    is LoadState.Success -> {
                        lifecycleScope.launch {
                            if(state.data.data != null) {
                                val data = state.data.data.get(0)
                                val downloadUrl = data.url
                                ketchDownload(downloadUrl, createFileName("IG", downloadUrl))
                            } else {
                                ToastUtil.showToast(this@IgDownloadActivity, getString(R.string.invalid_url))
                                showLoading(false)
                            }
                        }
                    }
                    is LoadState.Error -> {
                        showLoading(false)
                        showErrorMessage(true, state.message)
                    }
                }
            }
    }
    suspend fun ketchDownload(url: String, fileName: String) {
        ketch.download(
            url = url,
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path,
            fileName = fileName
        ).also {
               ketch.observeDownloadById(it).collect { dl ->
                   showDownloadSuccessOrFailed(dl.status, this@IgDownloadActivity)
                   showLoading(false)
               }
        }
    }
    fun showLoading(isVisible: Boolean) {
        if (isVisible) {
            binding.progressBar2.visibility = View.VISIBLE
        } else {
            binding.progressBar2.visibility = View.GONE
        }
    }private fun showErrorMessage(isVisible: Boolean, message: String = "") {
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