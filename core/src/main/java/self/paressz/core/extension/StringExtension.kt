package self.paressz.core.extension

import java.net.URLDecoder
import java.nio.charset.StandardCharsets

fun String.toHumanReadable() : String {
    var decodePath = URLDecoder.decode(this, StandardCharsets.UTF_8.toString())
    decodePath = decodePath.removePrefix("/tree/primary:")
    return decodePath
}