package self.paressz.pzdownloader.ui.fb

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
import androidx.lifecycle.lifecycleScope
import com.ketch.Ketch
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import self.paressz.core.repository.LoadState
import self.paressz.pzdownloader.R
import self.paressz.pzdownloader.databinding.ActivityFbDownloadBinding
import self.paressz.pzdownloader.util.ToastUtil
import self.paressz.pzdownloader.util.checkIsUrlBlank
import self.paressz.pzdownloader.util.createFileName
import self.paressz.pzdownloader.util.getKetch
import self.paressz.pzdownloader.util.showDownloadSuccessOrFailed
import self.paressz.pzdownloader.util.showErrorMesssage

@AndroidEntryPoint
class FbDownloadActivity : AppCompatActivity() {
    lateinit var binding: ActivityFbDownloadBinding
    lateinit var ketch: Ketch
    val viewModel : FbDownloadViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFbDownloadBinding.inflate(layoutInflater)
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
            val postUrl = binding.etUrl.text.toString()
            val isUrlBlank = checkIsUrlBlank(postUrl)
            if(!isUrlBlank) {
                downloadPost(postUrl)
            }
        }
    }
    fun downloadPost(postUrl: String) {
        viewModel.getDownloadUrl(postUrl).observe(this) { state ->
            when (state) {
                is LoadState.Loading -> {
                    showLoading(true)
                }

                is LoadState.Success -> {
                    val data = state.data.data
                    if (data != null) {
                        lifecycleScope.launch {
                            val downloadUrl = data.get(0).url
                            val fileName = createFileName("FB", downloadUrl)
                            ketchDownload(downloadUrl, fileName)
                        }
                    } else {
                        ToastUtil.showToast(this, getString(R.string.invalid_url))
                        showLoading(false)
                    }
                }
                is LoadState.Error -> {
                    showLoading(false)
                    showErrorMessage(true, state.message)

                }
            }
        }
    }
    suspend fun ketchDownload(url : String, fileName : String) {
        ketch.download(
            url = url,
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path,
            fileName = fileName
        ).also {
            ketch.observeDownloadById(it).collect { dl ->
                showDownloadSuccessOrFailed(dl.status, this)
                showLoading(false)
            }
        }
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
    fun showLoading(isVisible: Boolean) {
        if (isVisible) {
            binding.progressBar2.visibility = View.VISIBLE
        } else {
            binding.progressBar2.visibility = View.GONE
        }
    }
}