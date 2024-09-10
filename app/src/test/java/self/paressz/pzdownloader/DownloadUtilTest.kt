package self.paressz.pzdownloader

import org.junit.Test
import self.paressz.pzdownloader.util.createFileName
import java.text.SimpleDateFormat

class DownloadUtilTest {
    @Test
    fun `Create File Name Should Return expectedFileName`() {
        val date = SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis())
        val platform = "X"
        val expectedFileName = "PzDl-$platform-$date.mp4"
        assert(createFileName(platform) == expectedFileName)
    }
}