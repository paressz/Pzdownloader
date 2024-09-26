package self.paressz.core.model.ryzendesu

import com.google.gson.annotations.SerializedName

data class RyzenDesuFbResponse(

	@field:SerializedName("data")
	val data: List<DataItem>?,

	@field:SerializedName("status")
	val status: Boolean,

	@field:SerializedName("msg")
	val msg: String? = null,
) {
	data class DataItem(

		@field:SerializedName("thumbnail")
		val thumbnail: String,

		@field:SerializedName("shouldRender")
		val shouldRender: Boolean,

		@field:SerializedName("resolution")
		val resolution: String,

		@field:SerializedName("url")
		val url: String
	)
}

