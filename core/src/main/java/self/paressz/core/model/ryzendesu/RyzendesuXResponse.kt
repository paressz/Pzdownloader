package self.paressz.core.model.ryzendesu

import com.google.gson.annotations.SerializedName

data class RyzendesuXResponse(

	@field:SerializedName("media")
	val media: List<Media>? = null,

	@field:SerializedName("type")
	val type: String?,

	@field:SerializedName("status")
	val status: Boolean,

	@field:SerializedName("msg")
	val msg: String? = null,
) {
	sealed class Media {
		data class MultiType(
			@field:SerializedName("url") val url: String,
			@field:SerializedName("quality") val quality: String
		) : Media()
		data class Image(val url: String) : Media()
	}
}

