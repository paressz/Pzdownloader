package self.paressz.pzdownloader.ui.yt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioGroup
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
import self.paressz.core.model.ryzendesu.RyzendesuYtResponse
import self.paressz.core.repository.LoadState
import self.paressz.pzdownloader.R
import self.paressz.core.constants.VideoQualityConstants
import self.paressz.pzdownloader.databinding.ActivityYoutubeDownloadBinding
import self.paressz.pzdownloader.ui.BaseActivity
import self.paressz.pzdownloader.util.ToastUtil
import self.paressz.pzdownloader.util.createFileName
import self.paressz.pzdownloader.util.getKetch
import self.paressz.pzdownloader.util.showErrorMesssage
import self.paressz.pzdownloader.util.showLoading
import java.util.Arrays

@AndroidEntryPoint
class YoutubeDownloadActivity : BaseActivity(), View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private lateinit var binding: ActivityYoutubeDownloadBinding
    private val viewModel : YoutubeDownloadViewModel by viewModels()
    private lateinit var ketch : Ketch
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYoutubeDownloadBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        ketch = getKetch().build(this)
        setupSpinner()
        getSharedLink()
        binding.btnDownload.setOnClickListener(this)
        binding.btnPaste.setOnClickListener(this)
        binding.radioGroup.setOnCheckedChangeListener(this)
        binding.radioMainServer.isChecked = true
        ToastUtil.showToast(this, "Not Working Yet")
    }
    fun downloadYoutubeVideo(url: String, videoRes : String) {
        when (viewModel.chosenServer) {
            0 -> {
                viewModel.downloadYt(url, videoRes).observe(this) { state ->
                    when (state) {
                        is LoadState.Error -> {
                            showLoading(binding.progressBar2, false)
                            showErrorMesssage(binding.tvError, true, state.message)
                        }
                        is LoadState.Loading -> {
                            showLoading(binding.progressBar2,true)
                        }
                        is LoadState.Success -> {
                            showLoading(binding.progressBar2, false)
                            val url = state.data.url
                            val filename = state.data.filename
                            lifecycleScope.launch {
                                ketchDownload(url, filename)
                            }
                        }
                    }
                }
            }
            1 -> {
                viewModel.downloadYtFromBackup(url, videoRes).observe(this) { state ->
                    when (state) {
                        is LoadState.Error -> {
                            showLoading(binding.progressBar2, false)
                            showErrorMesssage(binding.tvError, true, state.message)
                        }
                        is LoadState.Loading -> {
                            showLoading(binding.progressBar2,true)
                        }
                        is LoadState.Success -> {
                            showLoading(binding.progressBar2, false)
                            val url = state.data.url
                            val filename = state.data.filename
                            lifecycleScope.launch {
                                ketchDownload(url, filename)
                            }
                        }
                    }
                }
            }
        }
    }
    fun downloadSelectedQuality(url : String) {
        when (viewModel.quality) {
           0 -> {
               downloadYoutubeVideo(url, VideoQualityConstants.QUALITY_LOW)
           }
            1 -> {
                downloadYoutubeVideo(url, VideoQualityConstants.QUALITY_MEDIUM)
            }
            2 -> {
                downloadYoutubeVideo(url, VideoQualityConstants.QUALITY_SD)
            }
            3 -> {
                downloadYoutubeVideo(url, VideoQualityConstants.QUALITY_HD)
            }
        }
    }
    suspend fun ketchDownload(url : String, fileName: String) : Int{
        return ketch.download(
            url = url,
            fileName = fileName,
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path,
        ).also {
            ketch.observeDownloadById(it).collect { dl ->
                if (dl.status == Status.STARTED)
                    ToastUtil.showToast(this, "Download Started")
                showLoading(binding.progressBar2, false)
            }
        }
    }
    fun setupSpinner() {
        ArrayAdapter.createFromResource(this, R.array.video_qualities, android.R.layout.simple_spinner_dropdown_item).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.qualitySpinner.adapter = it
        }
        binding.qualitySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, v: View?, pos: Int, id: Long) {
                viewModel.quality = pos
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.qualitySpinner.setSelection(0)
            }

        }
    }
    override fun onCheckedChanged(rg: RadioGroup?, id: Int) {
        val selectedServer = when(id) {
            binding.radioMainServer.id -> 0
            binding.radioBackupServer.id -> 1
            else -> 0
        }
        viewModel.chosenServer = selectedServer
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
               ToastUtil.showToast(this, "Not Working Yet")
//               if (binding.etUrl.text.isNotBlank()) {
//                   downloadSelectedQuality(binding.etUrl.text.toString())
//               }
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
                if (sharedLink != null && (sharedLink.contains("youtube.com") || sharedLink.contains("youtu.be")))
                    binding.etUrl.setText(sharedLink)
                else
                    ToastUtil.showToast(this, getString(R.string.invalid_url_x))
            }
        }
    }
}