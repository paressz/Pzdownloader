package self.paressz.core.model.ryzendesu

import com.google.gson.annotations.SerializedName

data class RyzendesuTiktokResponse(

    @field:SerializedName("msg")
    val msg: String,

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("data")
    val data: PostData? = null,
) {

    data class PostData(

        @field:SerializedName("play")
        val videoUrl: String? = null,

        @field:SerializedName("title")
        val title: String? = null,

        @field:SerializedName("wmplay")
        val watermarkedVideoUrl: String? = null,

        @field:SerializedName("cover")
        val cover: String? = null,

        @field:SerializedName("duration")
        val duration: Int? = null,

        @field:SerializedName("id")
        val id: String? = null,

        @field:SerializedName("author")
        val author: TiktokAuthorData? = null,

        @field:SerializedName("play_count")
        val playCount: Int? = null,

        @field:SerializedName("share_count")
        val shareCount: Int? = null,

        @field:SerializedName("size")
        val size: Int? = null,

        @field:SerializedName("wm_size")
        val watermarkedVideoSize: Int? = null
    )

    data class TiktokAuthorData(

        @field:SerializedName("unique_id")
        val uniqueId: String? = null,

        @field:SerializedName("nickname")
        val nickname: String? = null,

        @field:SerializedName("id")
        val id: String? = null,

        @field:SerializedName("avatar")
        val avatar: String? = null
    )
}


