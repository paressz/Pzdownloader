package self.paressz.pzdownloader.ui.tiktok

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.view.View.OnClickListener
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.ketch.Ketch
import com.ketch.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import self.paressz.core.repository.LoadState
import self.paressz.pzdownloader.R
import self.paressz.pzdownloader.databinding.ActivityTiktokDownloadBinding
import self.paressz.pzdownloader.ui.BaseActivity
import self.paressz.pzdownloader.util.ToastUtil
import self.paressz.pzdownloader.util.createFileName
import self.paressz.pzdownloader.util.getKetch
import self.paressz.pzdownloader.util.showDownloadSuccessOrFailed
import self.paressz.pzdownloader.util.showErrorMesssage
import self.paressz.pzdownloader.util.showLoading

@AndroidEntryPoint
class TiktokDownloadActivity : BaseActivity(), OnClickListener {
    private lateinit var binding: ActivityTiktokDownloadBinding
    private lateinit var ketch: Ketch
    private val viewModel: TiktokDownloadViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTiktokDownloadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        ketch = getKetch().build(this)
        getSharedLink()
        binding.btnDownload.setOnClickListener(this)
        binding.btnPaste.setOnClickListener(this)
    }
    private fun downloadPost(url : String) {
        viewModel.downloadVideo(url).observe(this) { state ->
            when(state) {
                is LoadState.Error -> {
                    showLoading(binding.progressBar2, false)
                    showErrorMesssage(binding.tvError, true, state.message)
                }
                LoadState.Loading -> {
                    showLoading(binding.progressBar2, true)
                }
                is LoadState.Success -> {
                    lifecycleScope.launch {
                        val responseData = state.data.data
                        if (responseData != null) {
                            val postUrl = responseData.watermarkedVideoUrl!!
                            ketchDownload(postUrl, createFileName("Tiktok", postUrl))
                        } else {
                            ToastUtil.showToast(
                                this@TiktokDownloadActivity,
                                getString(R.string.invalid_url)
                            )
                            showLoading(binding.progressBar2, false)
                        }
                    }
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
                if (dl.status == Status.STARTED)
                    ToastUtil.showToast(this, "Download Started")
                showLoading(binding.progressBar2, false)
            }
        }
    }
    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.main.windowToken, 0)
    }
    private fun getSharedLink() {
        if (intent != null && intent.action == Intent.ACTION_SEND) {
            intent.getStringExtra(Intent.EXTRA_TEXT).let { sharedLink ->
                if (sharedLink != null && sharedLink.contains("tiktok.com"))
                    binding.etUrl.setText(sharedLink)
                else
                    ToastUtil.showToast(this, getString(R.string.invalid_url_x))
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            binding.btnPaste.id -> {
                val pastedText = getTextFromClipboard()
                if(pastedText.isNotBlank())
                    binding.etUrl.setText(pastedText)
            }
            binding.btnDownload.id -> {
                showErrorMesssage(binding.tvError, false)
                hideKeyboard()
                val postUrl = binding.etUrl.text.toString()
                if(postUrl.isNotBlank())
                    downloadPost(postUrl)
            }
        }
    }
}