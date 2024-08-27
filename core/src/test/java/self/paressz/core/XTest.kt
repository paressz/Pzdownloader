package self.paressz.core

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import retrofit2.Call
import retrofit2.Callback
import self.paressz.core.model.XResponse
import self.paressz.core.model.XResponseAlter

class XTest {
    val url = "https://x.com/elonmusk/status/1827970423788535925"
    @Test
    fun downloadXVideo() {
        val mockResponse = XResponse(
            status = true,
            type = "video",
            media = listOf(
                XResponse.MediaItem(
                    url = "https://dl.snapcdn.app/get?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1cmwiOiJodHRwczovL3ZpZGVvLnR3aW1nLmNvbS9leHRfdHdfdmlkZW8vMTgyNjM5NDM3NzM0Mzg5MzUwNC9wdS92aWQvYXZjMS8xMjgweDcyMC90aEFpa0E0V0w5aGlhamhyLm1wND90YWc9MTIiLCJmaWxlbmFtZSI6IlNhdmVUd2l0dGVyLk5ldF90aEFpa0E0V0w5aGlhamhyXyg3MjBwKS5tcDQiLCJuYmYiOjE3MjQ3NDgzOTQsImV4cCI6MTcyNDc1MTk5NCwiaWF0IjoxNzI0NzQ4Mzk0fQ.nsJ1PuNWWyEVB7aLjBbHKLHoucR8OjIGxqFJsywcvVY",
                    quality = "720"
                )
            )
        )
        val service = Mockito.mock(XService::class.java)
        val call = Mockito.mock(Call::class.java) as Call<XResponse>
        Mockito.`when`(service.downloadXVideo(url)).thenReturn(call)
        Mockito.`when`(call.enqueue(Mockito.any())).thenAnswer { invocation ->
            val callback = invocation.getArgument<retrofit2.Callback<XResponse>>(0)
            callback.onResponse(call, retrofit2.Response.success(mockResponse))
        }
        val result = service.downloadXVideo(url)
        result.enqueue(object : Callback<XResponse>{
            override fun onResponse(p0: Call<XResponse>, p1: retrofit2.Response<XResponse>) {
                val response = p1.body()
                assertEquals(mockResponse, response)
            }

            override fun onFailure(p0: Call<XResponse>, p1: Throwable) {
                assertFalse("Request failed", false)
            }
        })
    }
    @Test
    fun downloadXVideoAlter() {
        val mockResponse = XResponseAlter(
            listOf(
                XResponseAlter.XResponseAlterItem(
                    width = "1280",
                    url = "https://twitsave.com/download?file=aHR0cHM6Ly92aWRlby50d2ltZy5jb20vZXh0X3R3X3ZpZGVvLzE4MjYzOTQzNzczNDM4OTM1MDQvcHUvdmlkL2F2YzEvMTI4MHg3MjAvdGhBaWtBNFdMOWhpYWpoci5tcDQ%2FdGFnPTEy",
                    height = "720"
                )
            )
        )
        val call = Mockito.mock(Call::class.java) as Call<XResponseAlter>
        val service = Mockito.mock(XService::class.java)
        Mockito.`when`(service.downloadXVideoAlter(url)).thenReturn(call)
        Mockito.`when`(call.enqueue(Mockito.any())).thenAnswer { invocation ->
            val callback = invocation.getArgument<retrofit2.Callback<XResponseAlter>>(0)
            callback.onResponse(call, retrofit2.Response.success(mockResponse))
        }
        val result = service.downloadXVideoAlter(url)
        result.enqueue(object : Callback<XResponseAlter> {
            override fun onResponse(
                p0: Call<XResponseAlter>,
                p1: retrofit2.Response<XResponseAlter>
            ) {
                val response = p1.body()
                assertEquals(mockResponse, response)
            }

            override fun onFailure(p0: Call<XResponseAlter>, p1: Throwable) {
                assertFalse("Request failed", false)
            }
        })
    }
}