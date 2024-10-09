package self.paressz.core.model.github

import com.google.gson.annotations.SerializedName

data class GithubRelease(

	@field:SerializedName("assets")
	val assets: List<Asset>,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("published_at")
	val publishedAt: String,

	@field:SerializedName("body")
	val body: String,

	@field:SerializedName("html_url")
	val releaseUrl : String,

	@field:SerializedName("tag_name")
	val tagName :  String
)
