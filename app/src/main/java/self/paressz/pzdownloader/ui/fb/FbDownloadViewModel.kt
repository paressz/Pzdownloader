package self.paressz.pzdownloader.ui.fb

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import self.paressz.core.model.FbResponse
import self.paressz.core.repository.DownloadRepository
import self.paressz.core.repository.LoadState
import javax.inject.Inject

@HiltViewModel
class FbDownloadViewModel
@Inject constructor(private val downloadRepository: DownloadRepository) : ViewModel() {
    fun getDownloadUrl(postUrl: String): LiveData<LoadState<FbResponse>> {
        lateinit var data: LiveData<LoadState<FbResponse>>
        viewModelScope.launch {
            data = downloadRepository.downloadFacebookVideo(postUrl)
        }
        return data
    }
}