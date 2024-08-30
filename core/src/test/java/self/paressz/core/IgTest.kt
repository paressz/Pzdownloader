package self.paressz.core

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import self.paressz.core.model.IgResponse
import self.paressz.core.network.InstagramService

class IgTest {
    @Test
    fun testIgDownload() {
        val service = Mockito.mock(InstagramService::class.java)
        val testUrl = "https%3A%2F%2Fwww.instagram.com%2Freel%2FC_Kbd15xIFy%2F%3Futm_source%3Dig_web_copy_link"
        val thumbnail = "https://d.rapidcdn.app/d?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1cmwiOiJodHRwczovL3Njb250ZW50LmNkbmluc3RhZ3JhbS5jb20vdi90NTEuMjg4NS0xNS80NTcxMjY4NzhfMTU0MzE1MTY3NjU1MjU1NV82MjQ3MDQwNDcyOTMwOTgzNzk2X24uanBnP3N0cD1kc3QtanBnX2UxNSZfbmNfaHQ9c2NvbnRlbnQuY2RuaW5zdGFncmFtLmNvbSZfbmNfY2F0PTEwMyZfbmNfb2hjPXo2dHlEUWoxS1I0UTdrTnZnSFJOZE1EJmVkbT1BUHMxN0NVQkFBQUEmY2NiPTctNSZvaD0wMF9BWUJuTFpBS1R4TEVocXIwb2dIbDhmVHBGMUNPWElwdnRLUndlUF9LcEE4bHVRJm9lPTY2RDM0ODUyJl9uY19zaWQ9MTBkMTNiIiwiZmlsZW5hbWUiOiJTbmFwc2F2ZS5hcHBfNDU3MTI2ODc4XzE1NDMxNTE2NzY1NTI1NTVfNjI0NzA0MDQ3MjkzMDk4Mzc5Nl9uLmpwZyJ9.7PP9ZcXT_4hErD91tlf5qWVG3oiilWiO91ffu62UM3g\""
        val downloadUrl = "https://d.rapidcdn.app/d?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1cmwiOiJodHRwczovL3Njb250ZW50LmNkbmluc3RhZ3JhbS5jb20vbzEvdi90MTYvZjEvbTg2LzdGNDJBMDAwRkQxNkUwQUQ2MTRDQTAyODZERkREM0E1X3ZpZGVvX2Rhc2hpbml0Lm1wND9lZmc9ZXlKeFpWOW5jbTkxY0hNaU9pSmJYQ0pwWjE5M1pXSmZaR1ZzYVhabGNubGZkblJ6WDI5MFpsd2lYU0lzSW5abGJtTnZaR1ZmZEdGbklqb2lkblJ6WDNadlpGOTFjbXhuWlc0dVkyeHBjSE11WXpJdU5UYzJMbUpoYzJWc2FXNWxJbjAmX25jX2NhdD0xMDgmdnM9MjUyODAyMjg3NzM5MTcyMV8zMjQ3OTk4NTcwJl9uY192cz1IQmtzRlFJWVVtbG5YM2h3ZGw5eVpXVnNjMTl3WlhKdFlXNWxiblJmYzNKZmNISnZaQzgzUmpReVFUQXdNRVpFTVRaRk1FRkVOakUwUTBFd01qZzJSRVpFUkROQk5WOTJhV1JsYjE5a1lYTm9hVzVwZEM1dGNEUVZBQUxJQVFBVkFoZzZjR0Z6YzNSb2NtOTFaMmhmWlhabGNuTjBiM0psTDBkR1ZrdFBhSE5zY1ZONlNUSkJVVWRCUzFwRU1tSTJTamRwVm5aaWNWOUZRVUZCUmhVQ0FzZ0JBQ2dBR0FBYkFCVUFBQ2JVcDdDMzRlbXBRQlVDS0FKRE15d1hRRTZNek16TXpNMFlFbVJoYzJoZlltRnpaV3hwYm1WZk1WOTJNUkVBZGY0SEFBJTNEJTNEJmNjYj05LTQmb2g9MDBfQVlCc3kzWklXRXZXZ2JVaTNBVXlWVDhZdFdRLXBRQW02U2VfR1hSVXRpenNnUSZvZT02NkNGM0RDNiZfbmNfc2lkPTEwZDEzYiIsImZpbGVuYW1lIjoiU25hcHNhdmUuYXBwXzdGNDJBMDAwRkQxNkUwQUQ2MTRDQTAyODZERkREM0E1X3ZpZGVvX2Rhc2hpbml0Lm1wNCJ9.7kGHCLTw-OuC2eJ5izVpI_jK7b6zzQw_ePMIwThQOwM&dl=1"
        val mockResponse = IgResponse(
            status = true,
            data = listOf(IgResponse.DataItem(
                thumbnail = thumbnail,
                url = downloadUrl
            ))
        )
        val call = Mockito.mock(Call::class.java) as Call<IgResponse>
        Mockito.`when`(service.donwloadInstagramVideo(testUrl)).thenReturn(call)
        Mockito.`when`(call.enqueue(Mockito.any())).thenAnswer { invocation ->
            val callback = invocation.getArgument<Callback<IgResponse>>(0)
            callback.onResponse(call, Response.success(mockResponse))
        }
        val result = service.donwloadInstagramVideo(testUrl)
        result.enqueue(object : Callback<IgResponse> {
            override fun onResponse(mCall: Call<IgResponse>, mResponse: Response<IgResponse>) {
                val response = mResponse.body()
                assertEquals(mockResponse, response)
            }

            override fun onFailure(p0: Call<IgResponse>, p1: Throwable) {
                assertFalse("Request failed", false)
            }

        })
    }
}