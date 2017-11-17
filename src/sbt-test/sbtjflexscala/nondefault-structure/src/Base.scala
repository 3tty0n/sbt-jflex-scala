import java.io.StringReader

object Base {
  class MyError(s: String) extends Throwable

  def error() = throw new MyError("error")

  type Var = String

  val yylex = new Yylex(new StringReader("s"))
}
