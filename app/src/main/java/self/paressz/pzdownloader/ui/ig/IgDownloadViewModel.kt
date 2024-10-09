package self.paressz.pzdownloader.ui.ig

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
<<<<<<< HEAD
import self.paressz.core.model.ryzendesu.RyzendesuIgResponse
=======
import self.paressz.core.model.ryzendesu.RyzenDesuIgResponse
>>>>>>> master
import self.paressz.core.repository.ryzendesu.RyzendesuDownloadRepository
import self.paressz.core.repository.LoadState
import javax.inject.Inject

@HiltViewModel
class IgDownloadViewModel
@Inject constructor(private val ryzendesuDownloadRepository: RyzendesuDownloadRepository) : ViewModel() {
<<<<<<< HEAD
    fun getDownloadUrl(url: String): LiveData<LoadState<RyzendesuIgResponse>> {
        lateinit var data: LiveData<LoadState<RyzendesuIgResponse>>
=======
    fun getDownloadUrl(url: String): LiveData<LoadState<RyzenDesuIgResponse>> {
        lateinit var data: LiveData<LoadState<RyzenDesuIgResponse>>
>>>>>>> master
        viewModelScope.launch {
            data = ryzendesuDownloadRepository.downloadInstagramVideo(url)
        }
        return data
    }
}