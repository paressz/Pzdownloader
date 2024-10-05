package self.paressz.pzdownloader.ui.fb

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import self.paressz.core.model.ryzendesu.RyzenDesuFbResponse
import self.paressz.core.repository.ryzendesu.RyzendesuDownloadRepository
import self.paressz.core.repository.LoadState
import javax.inject.Inject

@HiltViewModel
class FbDownloadViewModel
@Inject constructor(private val ryzendesuDownloadRepository: RyzendesuDownloadRepository) : ViewModel() {
    fun getDownloadUrl(postUrl: String): LiveData<LoadState<RyzenDesuFbResponse>> {
        lateinit var data: LiveData<LoadState<RyzenDesuFbResponse>>
        viewModelScope.launch {
            data = ryzendesuDownloadRepository.downloadFacebookVideo(postUrl)
        }
        return data
    }
}