package self.paressz.pzdownloader.ui.ig

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import self.paressz.core.model.IgResponse
import self.paressz.core.repository.DownloadRepository
import self.paressz.core.repository.LoadState
import javax.inject.Inject

@HiltViewModel
class IgDownloadViewModel
    @Inject
    constructor(private val downloadRepository: DownloadRepository)
    : ViewModel() {
    fun getDownloadUrl(url :String) : LiveData<LoadState<IgResponse>> {
        lateinit var data : LiveData<LoadState<IgResponse>>
        viewModelScope.launch {
            data = downloadRepository.downloadInstagramVideo(url)
        }
        return data
    }
}