package self.paressz.pzdownloader.ui.yt

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import self.paressz.core.model.ryzendesu.RyzendesuYtResponse
import self.paressz.core.repository.LoadState
import self.paressz.core.repository.ryzendesu.RyzendesuDownloadRepository
import javax.inject.Inject

@HiltViewModel
class YoutubeDownloadViewModel
@Inject constructor(private val ryzendesuDownloadRepository: RyzendesuDownloadRepository) : ViewModel() {
    var chosenServer = 0
    var quality = 0
    fun downloadYt(url : String, videoRes : String) : LiveData<LoadState<RyzendesuYtResponse>> {
        lateinit var data  : LiveData<LoadState<RyzendesuYtResponse>>
        viewModelScope.launch {
            data = ryzendesuDownloadRepository.downloadYoutubeVideo(url, videoRes)
        }
        return data
    }
    fun downloadYtFromBackup(url : String, videoRes : String) : LiveData<LoadState<RyzendesuYtResponse>> {
        lateinit var data  : LiveData<LoadState<RyzendesuYtResponse>>
        viewModelScope.launch {
            data = ryzendesuDownloadRepository.downloadYoutubeVideoFromBackup(url, videoRes)
        }
        return data
    }
}