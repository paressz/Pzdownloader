package self.paressz.core.model.github

import com.google.gson.annotations.SerializedName

data class Asset (

    @field:SerializedName("size")
    val size: Int,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("browser_download_url")
    val downloadUrl: String
)