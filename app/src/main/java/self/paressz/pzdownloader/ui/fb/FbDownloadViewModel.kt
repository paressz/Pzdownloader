package self.paressz.pzdownloader.ui.fb

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import self.paressz.core.model.ryzendesu.RyzendesuFbResponse
import self.paressz.core.repository.ryzendesu.RyzendesuDownloadRepository
import self.paressz.core.repository.LoadState
import javax.inject.Inject

@HiltViewModel
class FbDownloadViewModel
@Inject
constructor(private val ryzendesuDownloadRepository: RyzendesuDownloadRepository) : ViewModel() {

    var choosenServer = 0

    fun getDownloadUrl(postUrl: String): LiveData<LoadState<RyzendesuFbResponse>> {
        lateinit var data: LiveData<LoadState<RyzendesuFbResponse>>
        viewModelScope.launch {
            data = ryzendesuDownloadRepository.downloadFacebookVideo(postUrl)
        }
        return data
    }

    fun getDownloadUrlFromBackup(postUrl: String): LiveData<LoadState<RyzendesuFbResponse>> {
        lateinit var data: LiveData<LoadState<RyzendesuFbResponse>>
        viewModelScope.launch {
            data = ryzendesuDownloadRepository.downloadFacebookVideoFromBackup(postUrl)
        }
        return data
    }
}