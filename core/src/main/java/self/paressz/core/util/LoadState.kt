package self.paressz.core.util

sealed class LoadState<out R> private constructor() {
    object Loading : LoadState<Nothing>()
    data class Success <out T>(val data: T) : LoadState<T>()
    data class Error(val message: String) : LoadState<Nothing>()

}