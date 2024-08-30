package self.paressz.pzdownloader.ui.x

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import self.paressz.core.model.XResponse
import self.paressz.core.repository.DownloadRepository
import self.paressz.core.util.LoadState
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class XDownloadViewModel
@Inject constructor(
    private val downloadRepository: DownloadRepository
) : ViewModel() {
    fun downloadX(url: String) : LiveData<LoadState<XResponse>> {
        lateinit var data : LiveData<LoadState<XResponse>>
        viewModelScope.launch {
            data = downloadRepository.downloadXVideo(url)
        }
        return data
    }

    suspend fun downloadVideo(url : String) : InputStream = downloadRepository.downloadVideo(url)
}