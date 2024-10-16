package self.paressz.pzdownloader.ui.x

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import self.paressz.core.model.ryzendesu.RyzendesuXResponse
import self.paressz.core.repository.ryzendesu.RyzendesuDownloadRepository
import self.paressz.core.repository.LoadState
import javax.inject.Inject

@HiltViewModel
class XDownloadViewModel
@Inject
constructor(private val ryzendesuDownloadRepository: RyzendesuDownloadRepository) : ViewModel() {

    var choosenServer = 0

    fun downloadX(url: String): LiveData<LoadState<RyzendesuXResponse>> {
        lateinit var data: LiveData<LoadState<RyzendesuXResponse>>
        viewModelScope.launch {
            data = ryzendesuDownloadRepository.downloadXVideo(url)
        }
        return data
    }
    fun downloadXFromBackup(url: String): LiveData<LoadState<RyzendesuXResponse>> {
        lateinit var data: LiveData<LoadState<RyzendesuXResponse>>
        viewModelScope.launch {
            data = ryzendesuDownloadRepository.downloadXVideoFromBackup(url)
        }
        return data
    }
}