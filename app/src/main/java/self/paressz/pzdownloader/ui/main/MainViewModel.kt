package self.paressz.pzdownloader.ui.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import self.paressz.core.repository.github.GithubRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val githubRepository : GithubRepository) : ViewModel(){
    fun checkForUpdate() = githubRepository.checkForUpdate()
}