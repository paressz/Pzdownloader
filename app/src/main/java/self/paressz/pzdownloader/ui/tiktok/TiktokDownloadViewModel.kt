package self.paressz.pzdownloader.ui.tiktok

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import self.paressz.core.repository.ryzendesu.RyzendesuDownloadRepository
import javax.inject.Inject

@HiltViewModel
class TiktokDownloadViewModel
@Inject
constructor(private val ryzendesuDownloadRepository: RyzendesuDownloadRepository) : ViewModel()  {
    fun downloadVideo(url : String) = ryzendesuDownloadRepository.downloadTiktokVideo(url)
}