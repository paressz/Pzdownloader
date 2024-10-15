package self.paressz.pzdownloader.ui.ig

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import self.paressz.core.model.ryzendesu.RyzendesuIgResponse
import self.paressz.core.repository.ryzendesu.RyzendesuDownloadRepository
import self.paressz.core.repository.LoadState
import javax.inject.Inject

@HiltViewModel
class IgDownloadViewModel
@Inject
constructor(private val ryzendesuDownloadRepository: RyzendesuDownloadRepository) : ViewModel() {

    var choosenServer = 0

    fun getDownloadUrl(url: String): LiveData<LoadState<RyzendesuIgResponse>> {
        lateinit var data: LiveData<LoadState<RyzendesuIgResponse>>
        viewModelScope.launch {
            data = ryzendesuDownloadRepository.downloadInstagramVideo(url)
        }
        return data
    }

    fun getDownloadUrlFromBackup(url: String): LiveData<LoadState<RyzendesuIgResponse>> {
        lateinit var data: LiveData<LoadState<RyzendesuIgResponse>>
        viewModelScope.launch {
            data = ryzendesuDownloadRepository.downloadInstagramVideoFromBackup(url)
        }
        return data
    }
}