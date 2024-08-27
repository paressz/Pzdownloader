package self.paressz.core.model

import com.google.gson.annotations.SerializedName

data class IgResponse(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("status")
	val status: Boolean
) {
	data class DataItem(

		@field:SerializedName("thumbnail")
		val thumbnail: String,

		@field:SerializedName("url")
		val url: String
	)
}
