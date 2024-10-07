package self.paressz.core.model.ryzendesu

import com.google.gson.annotations.SerializedName

data class RyzenDesuIgResponse(

	@field:SerializedName("data")
	val data: List<DataItem>? = null,

	@field:SerializedName("status")
	val status: Boolean,

	@field:SerializedName("msg")
	val msg: String? = null,
) {
	data class DataItem(

		@field:SerializedName("thumbnail")
		val thumbnail: String,

		@field:SerializedName("url")
		val url: String
	)
}
