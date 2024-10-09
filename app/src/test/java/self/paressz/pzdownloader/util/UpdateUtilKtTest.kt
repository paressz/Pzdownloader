package self.paressz.pzdownloader.util

import org.junit.Test

class UpdateUtilKtTest {
    @Test
    fun `Should return true if latestVersion is higher than currentVersion`() {
        val latest = "v5.1.1.1".removePrefix("v")
        val current = "5.1.1"
        val actual = newVersionAvailable(latest, current)
        val expected = true
        assert(actual == expected)
    }
}