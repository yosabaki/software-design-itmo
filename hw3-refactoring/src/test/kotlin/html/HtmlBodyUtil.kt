package html

val SEP = System.lineSeparator()
val HTML_BODY_HEAD = "<html><body>$SEP"
val HTML_BODY_TAIL = "</body></html>$SEP"

fun wrapHtmlBody(content: String) = "$HTML_BODY_HEAD$content$HTML_BODY_TAIL"
