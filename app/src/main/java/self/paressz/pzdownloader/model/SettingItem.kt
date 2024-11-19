package self.paressz.pzdownloader.model

data class SettingItem(
    val title: String,
    val description: String,
    val onClick : () -> Unit
)