package self.paressz.core.model.ryzendesu

import com.google.gson.annotations.SerializedName

data class RyzenDesuXResponseAlter(
	@field:SerializedName("XResponseAlter")
	val xResponseAlter: List<XResponseAlterItem>
) {
	data class XResponseAlterItem(

		@field:SerializedName("width")
		val width: String,

		@field:SerializedName("url")
		val url: String,

		@field:SerializedName("height")
		val height: String
	)
}

