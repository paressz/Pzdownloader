package self.paressz.core.model

import com.google.gson.annotations.SerializedName

data class FbResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("status")
	val status: Boolean
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

