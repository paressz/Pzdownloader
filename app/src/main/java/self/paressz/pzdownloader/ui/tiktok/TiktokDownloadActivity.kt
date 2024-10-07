package self.paressz.pzdownloader.ui.tiktok

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ketch.Ketch
import dagger.hilt.android.AndroidEntryPoint
import self.paressz.pzdownloader.R
import self.paressz.pzdownloader.databinding.ActivityTiktokDownloadBinding
import self.paressz.pzdownloader.ui.BaseActivity
import self.paressz.pzdownloader.util.ToastUtil
import self.paressz.pzdownloader.util.showErrorMesssage
@AndroidEntryPoint
class TiktokDownloadActivity : BaseActivity(), OnClickListener {
    private lateinit var binding: ActivityTiktokDownloadBinding
    private lateinit var ketch: Ketch
    private val viewModel: TiktokDownloadViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tiktok_download)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        getSharedLink()
    }
    private fun downloadPost(url : String) {

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