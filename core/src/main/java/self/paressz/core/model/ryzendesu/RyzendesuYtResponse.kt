package self.paressz.core.model.ryzendesu

import com.google.gson.annotations.SerializedName

data class RyzendesuYtResponse(

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("filename")
	val filename: String
)
