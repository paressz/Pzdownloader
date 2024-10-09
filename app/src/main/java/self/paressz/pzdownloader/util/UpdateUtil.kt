package self.paressz.pzdownloader.util

fun newVersionAvailable(latestVersion: String, currentVersion: String) : Boolean {
    val latestParts = latestVersion.split(".").map { it.toInt() }
    val currentParts = currentVersion.split(".").map { it.toInt() }
    for (i in 0 until maxOf(latestParts.size, currentParts.size)) {
        val latestPart = latestParts.getOrNull(i) ?: 0
        val currentPart = currentParts.getOrNull(i) ?: 0
        if (latestPart > currentPart) return true
        if (latestPart < currentPart) return false
    }
    return false
}