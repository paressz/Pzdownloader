package self.paressz.pzdownloader.ui.x

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import self.paressz.core.model.ryzendesu.RyzenDesuXResponse
import self.paressz.core.repository.ryzendesu.RyzendesuDownloadRepository
import self.paressz.core.repository.LoadState
import javax.inject.Inject

@HiltViewModel
class XDownloadViewModel
@Inject constructor(private val ryzendesuDownloadRepository: RyzendesuDownloadRepository) : ViewModel() {
    fun downloadX(url: String): LiveData<LoadState<RyzenDesuXResponse>> {
        lateinit var data: LiveData<LoadState<RyzenDesuXResponse>>
        viewModelScope.launch {
            data = ryzendesuDownloadRepository.downloadXVideo(url)
        }
        return data
    }
}