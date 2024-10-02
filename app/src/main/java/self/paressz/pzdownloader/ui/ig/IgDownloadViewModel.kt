package self.paressz.pzdownloader.ui.ig

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import self.paressz.core.model.ryzendesu.RyzenDesuIgResponse
import self.paressz.core.repository.DownloadRepository
import self.paressz.core.repository.LoadState
import javax.inject.Inject

@HiltViewModel
class IgDownloadViewModel
@Inject constructor(private val downloadRepository: DownloadRepository) : ViewModel() {
    fun getDownloadUrl(url: String): LiveData<LoadState<RyzenDesuIgResponse>> {
        lateinit var data: LiveData<LoadState<RyzenDesuIgResponse>>
        viewModelScope.launch {
            data = downloadRepository.downloadInstagramVideo(url)
        }
        return data
    }
}