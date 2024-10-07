package self.paressz.pzdownloader.ui.x

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.ketch.Ketch
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import self.paressz.core.model.ryzendesu.RyzendesuXResponse
import self.paressz.core.repository.LoadState
import self.paressz.pzdownloader.R
import self.paressz.pzdownloader.databinding.ActivityXDownloadBinding
import self.paressz.pzdownloader.ui.BaseActivity
import self.paressz.pzdownloader.util.ToastUtil
import self.paressz.pzdownloader.util.createFileName
import self.paressz.pzdownloader.util.getKetch
import self.paressz.pzdownloader.util.showErrorMesssage
import self.paressz.pzdownloader.util.showLoading

@AndroidEntryPoint
class XDownloadActivity : BaseActivity(), OnClickListener {
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
        getSharedLink()
        ketch = getKetch().build(this)
        binding.btnDownload.setOnClickListener(this)
        binding.btnPaste.setOnClickListener(this)
    }

    private fun downloadPost(url: String) {
        viewModel.downloadX(url).observe(this) { state ->
            when (state) {
                is LoadState.Loading -> {
                    showLoading(binding.progressBar,true)
                }

                is LoadState.Success -> {
                    showLoading(binding.progressBar, false)
                    lifecycleScope.launch {
                        val data = state.data
                        if (data.media != null) {
                            val medias = data.media!!
                            for (i in medias.indices) {
                                Log.d("DL LOOP", "downloadVideoLoop: $i")
                                if(medias[i] is RyzendesuXResponse.Media.MultiType) {
                                    val media = medias[i] as RyzendesuXResponse.Media.MultiType
                                    val fileName = createFileName("X", media.url)
                                    ketchDownload(media.url, "${i}_${fileName}")
                                } else if (medias[i] is RyzendesuXResponse.Media.Image){
                                    val media = medias[i] as RyzendesuXResponse.Media.Image
                                    val fileName = createFileName("X", media.url)
                                    ketchDownload(media.url, "${i}_${fileName}")
                                }
                            }
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

    private fun ketchDownload(downloadUrl: String, fileName: String) : Int {
        return ketch.download(
            url = downloadUrl,
            fileName = fileName,
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path
        )
    }
    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.main.windowToken, 0)
    }
    private fun getSharedLink() {
        if (intent != null && intent.action == Intent.ACTION_SEND) {
            intent.getStringExtra(Intent.EXTRA_TEXT).let { sharedLink ->
                if (sharedLink != null && sharedLink.contains("x.com"))
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