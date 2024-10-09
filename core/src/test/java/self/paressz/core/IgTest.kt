package self.paressz.core

import android.util.Log
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import self.paressz.core.model.ryzendesu.RyzendesuIgResponse
import self.paressz.core.network.ryzendesu.RyzendesuApiClient
import self.paressz.core.network.ryzendesu.RyzendesuInstagramService
import java.util.concurrent.CountDownLatch

class IgTest {
    @Test
    fun testIgDownload() {
        val service = RyzendesuApiClient.getInstagramService()
        val testUrl = "https://www.instagram.com/reel/C_uvsRKyRp1/"
        val mockResponse = RyzendesuIgResponse(
            status = true,
            data = listOf(
                RyzendesuIgResponse.DataItem(
                thumbnail = "thumbnail",
                url = "downloadUrl"
            ))
        )
        val latch = CountDownLatch(1)
        service.donwloadInstagramVideo(testUrl).enqueue(object : Callback<RyzendesuIgResponse> {
            override fun onResponse(mCall: Call<RyzendesuIgResponse>, mResponse: Response<RyzendesuIgResponse>) {
                val response = mResponse.body()
                latch.countDown()
                println("IgTest " + "onResponse: ${response?.status}")
                assertEquals(mockResponse.status, response?.status)
            }

            override fun onFailure(p0: Call<RyzendesuIgResponse>, p1: Throwable) {
                Assert.fail("Test failed: onFailure $p1")
                latch.countDown()
            }
        })
        latch.await()
    }
}