import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) : List<String> {
    val file = File("src", "$name.txt")
    if(!file.exists()) {
        file.writeText("")
    }
    return file.readLines()
}

fun output(o: Any) {
    println(o)

    Toolkit.getDefaultToolkit()
        .getSystemClipboard()
        .setContents(
            StringSelection(o.toString()),
            null
        )
}

fun <T> T.debug(): T {
    println(this)
    return this
}

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)
