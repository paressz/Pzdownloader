package self.paressz.pzdownloader.util

import android.content.Context
import android.widget.Toast
object ToastUtil {
    var toast: Toast? = null

    fun makeText(context: Context, text : String, duration : Int = Toast.LENGTH_SHORT) : Toast {
        if (toast != null) {
            toast!!.cancel()
        } else {
            toast = Toast.makeText(context, text, duration)
        }
        return toast!!
    }
    fun show() = toast!!.show()
    fun showToast(context: Context, text: String, duration: Int = Toast.LENGTH_SHORT) = makeText(context, text, duration).show()
}