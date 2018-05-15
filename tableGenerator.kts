import java.io.File
import java.io.InputStream
import java.io.OutputStreamWriter

val prefix = "https://github.com/alxgcrz/alexandria"
val inputStream: InputStream = File("README.md").inputStream()
val lineList = mutableListOf<String>()
val links = mutableListOf<String>()
val outputStream: OutputStreamWriter = File("table.md").writer()

inputStream.bufferedReader().useLines { lines -> lines.forEach { lineList.add(it) } }

lineList.forEach {
    if(it.startsWith("##") && !it.contains("Table")) {
        buildMarkdown(it)
    }
}

outputStream.write("# Table of Contents\n\n")

links.forEach {
    outputStream.write(it + "\n")
}

inputStream.close()
outputStream.close()

fun buildMarkdown(str: String) {
    val pre = str.split("-")
    when (pre.size) {
        1 -> links.add("* [${cleanString(pre[0])}]${buildGithubLink(pre[0])}")
        2 -> links.add("  * [${cleanString(pre[0])} - ${cleanString(pre[1])}]${buildGithubLink(pre[0], pre[1])}")
        3 -> links.add("    * [${cleanString(pre[0])} - ${cleanString(pre[1])} - ${cleanString(pre[2])}]${buildGithubLink(pre[0], pre[1], pre[2])}")
        4 -> links.add("      * [${cleanString(pre[0])} - ${cleanString(pre[1])} - ${cleanString(pre[2])} - ${cleanString(pre[3])}]${buildGithubLink(pre[0], pre[1], pre[2], pre[3])}")
    }
}

fun cleanString(str: String): String {
    return str.replace("[", "")
            .replace("]", "")
            .replace("#", "")
            .trim()
}

fun buildGithubLink(vararg str: String) = when (str.size) {
    1 -> "($prefix#${cleanString(str[0])})".toLowerCase()
    2 -> "($prefix#${cleanString(str[0])}---${cleanString(str[1])})".toLowerCase().replace(" ", "-")
    3 -> "($prefix#${cleanString(str[0])}---${cleanString(str[1])}---${cleanString(str[2])})".toLowerCase().replace(" ", "-")
    else -> "($prefix#${cleanString(str[0])}---${cleanString(str[1])}---${cleanString(str[2])}---${cleanString(str[3])})".toLowerCase().replace(" ", "-")
}
