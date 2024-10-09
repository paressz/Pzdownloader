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
<<<<<<< HEAD
import self.paressz.core.model.ryzendesu.RyzendesuIgResponse
import self.paressz.core.network.ryzendesu.RyzendesuApiClient
import self.paressz.core.network.ryzendesu.RyzendesuInstagramService
import java.util.concurrent.CountDownLatch
=======
import self.paressz.core.model.ryzendesu.RyzenDesuIgResponse
import self.paressz.core.network.ryzendesu.RyzendesuInstagramService
>>>>>>> master

class IgTest {
    @Test
    fun testIgDownload() {
<<<<<<< HEAD
        val service = RyzendesuApiClient.getInstagramService()
=======
        val service = Mockito.mock(RyzendesuInstagramService::class.java)
>>>>>>> master
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