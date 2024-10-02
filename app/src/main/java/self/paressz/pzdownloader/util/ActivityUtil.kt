package self.paressz.pzdownloader.util

import android.content.Intent
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView

fun checkIsUrlBlank(url : String) : Boolean = url.isBlank()
fun showLoading(progressBar: ProgressBar, isLoading : Boolean) {
    progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
}
fun showErrorMesssage(tv : TextView, isError : Boolean, message : String = "") {
    if (isError) {
        tv.text = message
        tv.visibility = View.VISIBLE
    } else {
        tv.visibility = View.GONE
    }
}
fun handleShareIntent(intent: Intent,  handler : (String) -> Unit) : Int {
    if(intent.action == Intent.ACTION_SEND) {
        intent.getStringExtra(Intent.EXTRA_TEXT).let { sharedLink ->
            if(sharedLink != null && sharedLink.contains("instagram.com")) {
                handler(sharedLink)
                return 1
            }
            else return 0
        }
    }
    return -1
}
