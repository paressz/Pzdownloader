package self.paressz.core

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import self.paressz.core.model.FbResponse
import self.paressz.core.network.ryzendesu.FacebookService

class FbTest {
    @Test
    fun fbDonwloadTest() {
        val testUrl = "https%3A%2F%2Fwww.facebook.com%2Fshare%2Fv%2FdPaqQ4xEWgC2w3Me%2F"
        val service = Mockito.mock(FacebookService::class.java)
        val durationMs = 239820
        val sdUrl = "https://video-msp1-1.xx.fbcdn.net/v/t42.1790-2/456594619_991535929320877_7093886515603724277_n.mp4?_nc_cat=110&ccb=1-7&_nc_sid=55d0d3&efg=eyJybHIiOjU4OCwicmxhIjoxNDcwLCJ2ZW5jb2RlX3RhZyI6InN2ZV9zZCIsInZpZGVvX2lkIjoxMDU1MDQxOTQ1NTQwMDQ4fQ%3D%3D&_nc_ohc=xL0esIrM4CgQ7kNvgGPPaAE&rl=588&vabr=327&_nc_ht=video-msp1-1.xx&oh=00_AYC_xHHefq-T3u-EE726uSCGHk0b_xBNg19Gx2uMLKa7Iw&oe=66D35476"
        val hdUrl = "https://video-msp1-1.xx.fbcdn.net/o1/v/t2/f2/m69/An9HeMu5_ReUp1SOVRA0w7YmOvum8LmZhp-6vvBagVmnE-PQObfpOn70LN_cfHnlUn4kmZypuS22sLfsmahMyOCk.mp4?efg=eyJ2ZW5jb2RlX3RhZyI6Im9lcF9oZCJ9&_nc_ht=video-msp1-1.xx.fbcdn.net&_nc_cat=108&strext=1&vs=e5956d870f1699c7&_nc_vs=HBksFQIYOnBhc3N0aHJvdWdoX2V2ZXJzdG9yZS9HQXJiTlJ1dGp5WEdSWk1DQU1Wa2Jyd0xkeFVhYm1kakFBQUYVAALIAQAVAhg6cGFzc3Rocm91Z2hfZXZlcnN0b3JlL0dHQjVOeHRfanNXSnRSSUVBS3BzTDQ1RC05bHlickZxQUFBRhUCAsgBAEsHiBJwcm9ncmVzc2l2ZV9yZWNpcGUBMQ1zdWJzYW1wbGVfZnBzABB2bWFmX2VuYWJsZV9uc3ViACBtZWFzdXJlX29yaWdpbmFsX3Jlc29sdXRpb25fc3NpbQAoY29tcHV0ZV9zc2ltX29ubHlfYXRfb3JpZ2luYWxfcmVzb2x1dGlvbgAddXNlX2xhbmN6b3NfZm9yX3ZxbV91cHNjYWxpbmcAEWRpc2FibGVfcG9zdF9wdnFzABUAJQAcjBdAAAAAAAAAABERAAAAJu73iL%2FwjpUFFQIoAkMzGAt2dHNfcHJldmlldxwXQC4AAAAAAAAYGWRhc2hfaDI2NC1iYXNpYy1nZW4yXzcyMHASABgYdmlkZW9zLnZ0cy5jYWxsYmFjay5wcm9kOBJWSURFT19WSUVXX1JFUVVFU1QbCogVb2VtX3RhcmdldF9lbmNvZGVfdGFnBm9lcF9oZBNvZW1fcmVxdWVzdF90aW1lX21zATAMb2VtX2NmZ19ydWxlB3VubXV0ZWQTb2VtX3JvaV9yZWFjaF9jb3VudAUxMzg4NBFvZW1faXNfZXhwZXJpbWVudAAMb2VtX3ZpZGVvX2lkEDExODM3MTE5MDYyOTYxODESb2VtX3ZpZGVvX2Fzc2V0X2lkDzUyNDEwODYzNjY2NTY2NhVvZW1fdmlkZW9fcmVzb3VyY2VfaWQQMTQ1MzgwOTk4ODYwNzQ3ORxvZW1fc291cmNlX3ZpZGVvX2VuY29kaW5nX2lkEDIxODU1NzEyOTE4MTU5NDUOdnRzX3JlcXVlc3RfaWQAJQIcACXEARsHiAFzBDUwODkCY2QKMjAyNC0wOC0yMgNyY2IFMTM4MDADYXBwFEZhY2Vib29rIGZvciBBbmRyb2lkAmN0DkZCX1NIT1JUU19QT1NUE29yaWdpbmFsX2R1cmF0aW9uX3MCMTUCdHMVcHJvZ3Jlc3NpdmVfZW5jb2RpbmdzAA%3D%3D&ccb=9-4&oh=00_AYBGIFXG5C-_rO3hmfq8541ZLWn1sLjNeHhAsW8juKrEmw&oe=66CF559B&_nc_sid=1d576d&_nc_rid=388705431548063&_nc_store_type=1"
        val title = "magnanakaw"
        val thumbnailUrl = "https://scontent-msp1-1.xx.fbcdn.net/v/t15.5256-10/456545008_422644763579538_7333053551813026328_n.jpg?_nc_cat=108&ccb=1-7&_nc_sid=50ce42&_nc_ohc=aksPSSFXrLAQ7kNvgE9upHS&_nc_ht=scontent-msp1-1.xx&oh=00_AYBsQwFLIiHS7saYJFcvhNyi4O8bHmyYAQ9Kny_96qUQfg&oe=66D36330"
        val mockResponse = FbResponse(durationMs, sdUrl, thumbnailUrl, title, hdUrl, testUrl)
        val mCall = Mockito.mock(Call::class.java) as Call<FbResponse>
        Mockito.`when`(service.downloadFacebookVideo(testUrl)).thenReturn(mCall)
        Mockito.`when`(mCall.enqueue(Mockito.any())).thenAnswer {
            val callback = it.getArgument<retrofit2.Callback<FbResponse>>(0)
            callback.onResponse(mCall, retrofit2.Response.success(mockResponse))
        }
        val mResult = service.downloadFacebookVideo(testUrl)
        mResult.enqueue(object : Callback<FbResponse> {
            override fun onResponse(p0: Call<FbResponse>, p1: Response<FbResponse>) {
                assertEquals(mockResponse, p1.body())
            }

            override fun onFailure(p0: Call<FbResponse>, p1: Throwable) {
                assertFalse("Request failed", false)
            }

        })
    }
}