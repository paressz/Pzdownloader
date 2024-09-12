package self.paressz.pzdownloader

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Test
import self.paressz.pzdownloader.util.createFileName
import self.paressz.pzdownloader.util.guessFileType
import java.text.SimpleDateFormat

class DownloadUtilTest {
    val url = "https://pbs.twimg.com/media/GXL5oYUbMAEIMQA?format=jpg&name=4096x4096"
    @Test
    fun `Create File Name Should Return expectedFileName`() {
        CoroutineScope(Dispatchers.IO).launch {
            val time = System.currentTimeMillis()
            val date = SimpleDateFormat("yyyyMMddHHmmss").format(time)
            val platform = "X"
            val type = guessFileType(url)
            val expectedFileName = "PzDl_${platform}_$date.$type"
            val actual = createFileName(platform, url, time)
            println(expectedFileName)
            println(actual)
            assert(actual == expectedFileName)
        }
    }
    @Test
    fun `guessFileType should return jpg`() {
        val actual = guessFileType(url)
        val expected = "jpg"
        assert(actual == expected)
    }
}