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

fun output(o: Any, expected: Any = "") {
    if(expected != "") {
        check(o == expected) { "Expected: $expected but got $o"}
    }
    println(o)

    Toolkit.getDefaultToolkit()
        .getSystemClipboard()
        .setContents(
            StringSelection(o.toString()),
            null
        )
}

fun formatNanos(n: Long) : String {
    return if (n < 900_000) {
        "$n ns"
    } else if (n < 900_000_000) {
        "${"%.3f".format(n/1_000_000.0)} ms"
    } else {
        "${"%.3f".format(n/1_000_000_000.0)} s"
    }
}

fun <T> T.debug(): T {
    println(this)
    return this
}

fun String.allInts() = allIntsInString(this)
fun allIntsInString(line: String): List<Int> {
    return """-?\d+""".toRegex().findAll(line)
        .map { it.value.toInt() }
        .toList()
}

fun <T> Iterable<T>.debug(): Iterable<T> {
    this.forEach { println(it) }
    return this
}


/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)
