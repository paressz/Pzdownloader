package self.paressz.core

import com.google.gson.JsonObject
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import self.paressz.core.model.ryzendesu.RyzendesuXResponse
import self.paressz.core.model.ryzendesu.RyzendesuXResponseAlter
import self.paressz.core.network.ryzendesu.RyzendesuApiClient
import self.paressz.core.network.ryzendesu.RyzendesuXService
import java.util.concurrent.CountDownLatch

class XTest {
    val url = "https://x.com/Paressz/status/1829197614912508167"
    @Test
    fun downloadXVideo() {
        val service = RyzendesuApiClient.getXService()
        val latch = CountDownLatch(1)
        val mockResponse = RyzendesuXResponse(
            status = true,
            type = "video",
            media = listOf(
                RyzendesuXResponse.Media.MultiType(
                    url = "https://dl.snapcdn.app/get?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1cmwiOiJodHRwczovL3ZpZGVvLnR3aW1nLmNvbS9leHRfdHdfdmlkZW8vMTgyNjM5NDM3NzM0Mzg5MzUwNC9wdS92aWQvYXZjMS8xMjgweDcyMC90aEFpa0E0V0w5aGlhamhyLm1wND90YWc9MTIiLCJmaWxlbmFtZSI6IlNhdmVUd2l0dGVyLk5ldF90aEFpa0E0V0w5aGlhamhyXyg3MjBwKS5tcDQiLCJuYmYiOjE3MjQ3NDgzOTQsImV4cCI6MTcyNDc1MTk5NCwiaWF0IjoxNzI0NzQ4Mzk0fQ.nsJ1PuNWWyEVB7aLjBbHKLHoucR8OjIGxqFJsywcvVY",
                    quality = "720"
                )
            )
        )
        val result = service.downloadXVideo(url)
        result.enqueue(object : Callback<JsonObject> {
            override fun onResponse(p0: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful && response.body() != null) {
                    val jsonResponse = response.body()!!
                    jsonResponse.let {
                        val type = it.get("type")?.asString
                        val status = it.get("status")?.asBoolean
                        val msg = it.get("msg")?.asString
                        val medias = it.get("media")?.asJsonArray
                        val mediaItem = mutableListOf<RyzendesuXResponse.Media>()
                        if(type == "video") {
                            medias?.forEach { media ->
                                val mediaObj = media.asJsonObject
                                val url = mediaObj.get("url").asString
                                val quality = mediaObj.get("quality").asString
                                mediaItem.add(RyzendesuXResponse.Media.MultiType(url, quality))
                            }
                        } else if (type == "image") {
                            medias?.forEach { media ->
                                val imageUrl = media.asString
                                mediaItem.add(RyzendesuXResponse.Media.Image(imageUrl))
                            }
                        }
                        if(status != null) {
                            val actual = mediaItem[0] as RyzendesuXResponse.Media.MultiType
                            val expected = mockResponse.media!![0] as RyzendesuXResponse.Media.MultiType
                            assertEquals(actual.quality, expected.quality)
                        }
                        else {
                            Assert.fail("Status is null")
                        }
                        latch.countDown()
                    }
                } else {
                    Assert.fail("${response.code()}: ${response.message()}")
                    latch.countDown()
                }
                try {
                    latch.await()
                } catch (e : Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(p0: Call<JsonObject>, t: Throwable) {
                println(t.printStackTrace())
                Assert.fail("Request failed")
                latch.countDown()
                try {
                    latch.await()
                } catch (e : Exception) {
                    e.printStackTrace()
                }
            }
        })
    }
    @Test
    fun downloadXVideoAlter() {
        val mockResponse = RyzendesuXResponseAlter(
            listOf(
                RyzendesuXResponseAlter.XResponseAlterItem(
                    width = "1280",
                    url = "https://twitsave.com/download?file=aHR0cHM6Ly92aWRlby50d2ltZy5jb20vZXh0X3R3X3ZpZGVvLzE4MjYzOTQzNzczNDM4OTM1MDQvcHUvdmlkL2F2YzEvMTI4MHg3MjAvdGhBaWtBNFdMOWhpYWpoci5tcDQ%2FdGFnPTEy",
                    height = "720"
                )
            )
        )
        val call = Mockito.mock(Call::class.java) as Call<RyzendesuXResponseAlter>
        val service = Mockito.mock(RyzendesuXService::class.java)
        Mockito.`when`(service.downloadXVideoAlter(url)).thenReturn(call)
        Mockito.`when`(call.enqueue(Mockito.any())).thenAnswer { invocation ->
            val callback = invocation.getArgument<retrofit2.Callback<RyzendesuXResponseAlter>>(0)
            callback.onResponse(call, retrofit2.Response.success(mockResponse))
        }
        val result = service.downloadXVideoAlter(url)
        result.enqueue(object : Callback<RyzendesuXResponseAlter> {
            override fun onResponse(
                p0: Call<RyzendesuXResponseAlter>,
                p1: retrofit2.Response<RyzendesuXResponseAlter>
            ) {
                val response = p1.body()
                assertEquals(mockResponse, response)
            }

            override fun onFailure(p0: Call<RyzendesuXResponseAlter>, p1: Throwable) {
                assertFalse("Request failed", false)
            }
        })
    }
}