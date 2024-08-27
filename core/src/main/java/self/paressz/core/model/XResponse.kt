package self.paressz.core.model

import com.google.gson.annotations.SerializedName

data class XResponse(

	@field:SerializedName("media")
	val media: List<MediaItem>,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("status")
	val status: Boolean
) {
	data class MediaItem(

		@field:SerializedName("url")
		val url: String,

		@field:SerializedName("quality")
		val quality: String
	)
}

