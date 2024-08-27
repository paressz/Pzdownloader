package self.paressz.core.model

import com.google.gson.annotations.SerializedName

data class FbResponse(
	@field:SerializedName("durationMs")
	val durationMs: Int,
	@field:SerializedName("sd")
	val sd: String,
	@field:SerializedName("thumbnail")
	val thumbnail: String,
	@field:SerializedName("title")
	val title: String,
	@field:SerializedName("hd")
	val hd: String,
	@field:SerializedName("url")
	val url: String
)

