package self.paressz.core.model.ryzendesu

import com.google.gson.annotations.SerializedName

data class RyzenDesuXResponse(

	@field:SerializedName("media")
	val media: List<MediaItem>? = null,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("status")
	val status: Boolean,

	@field:SerializedName("msg")
	val msg: String? = null,
) {
	data class MediaItem(

		@field:SerializedName("url")
		val url: String,

		@field:SerializedName("quality")
		val quality: String
	)
}

