package self.paressz.core.model

import com.google.gson.annotations.SerializedName

data class TtResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("processed_time")
	val processedTime: Int

) {
	data class Data(
		@field:SerializedName("play")
		val play: String,

		@field:SerializedName("comment_count")
		val commentCount: Int,

		@field:SerializedName("title")
		val title: String,

		@field:SerializedName("play_count")
		val playCount: Int,

		@field:SerializedName("collect_count")
		val collectCount: Int,

		@field:SerializedName("wmplay")
		val wmplay: String,

		@field:SerializedName("download_count")
		val downloadCount: Int,

		@field:SerializedName("cover")
		val cover: String,

		@field:SerializedName("duration")
		val duration: Int,

		@field:SerializedName("share_count")
		val shareCount: Int,

		@field:SerializedName("music")
		val music: String,

		@field:SerializedName("size")
		val size: Int,

		@field:SerializedName("music_info")
		val musicInfo: MusicInfo,

		@field:SerializedName("digg_count")
		val diggCount: Int,

		@field:SerializedName("id")
		val id: String,

		@field:SerializedName("region")
		val region: String,

		@field:SerializedName("wm_size")
		val wmSize: Int
	)
	data class MusicInfo(

		@field:SerializedName("play")
		val play: String,

		@field:SerializedName("cover")
		val cover: String,

		@field:SerializedName("duration")
		val duration: Int,

		@field:SerializedName("original")
		val original: Boolean,

		@field:SerializedName("author")
		val author: String,

		@field:SerializedName("album")
		val album: String,

		@field:SerializedName("id")
		val id: String,

		@field:SerializedName("title")
		val title: String
	)
}

