package self.paressz.pzdownloader.ui.tiktok

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import self.paressz.core.model.ryzendesu.RyzendesuTiktokResponse
import self.paressz.core.repository.LoadState
import self.paressz.core.repository.ryzendesu.RyzendesuDownloadRepository
import javax.inject.Inject

@HiltViewModel
class TiktokDownloadViewModel
@Inject
constructor(private val ryzendesuDownloadRepository: RyzendesuDownloadRepository) : ViewModel()  {

    var choosenServer = 0

    fun downloadVideo(url : String): LiveData<LoadState<RyzendesuTiktokResponse>> {
        lateinit var data : LiveData<LoadState<RyzendesuTiktokResponse>>
        viewModelScope.launch {
            data = ryzendesuDownloadRepository.downloadTiktokVideo(url)
        }
        return data
    }

    fun downloadVideoFromBackup(url : String): LiveData<LoadState<RyzendesuTiktokResponse>> {
        lateinit var data : LiveData<LoadState<RyzendesuTiktokResponse>>
        viewModelScope.launch {
            data = ryzendesuDownloadRepository.downloadTiktokVideoFromBackup(url)
        }
        return data
    }
}