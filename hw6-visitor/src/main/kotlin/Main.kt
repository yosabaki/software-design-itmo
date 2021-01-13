import token.Tokenizer
import visitor.CalcVisitor
import visitor.ParserVisitor
import visitor.PrintVisitor

fun main() {
    val expression = readLine()!!
    val tokens = Tokenizer().tokenize(expression)
    val parsed = ParserVisitor().parse(tokens)
    println(PrintVisitor().print(parsed))
    println(CalcVisitor().eval(parsed))
}