object Base {
  class MyError(s: String) extends Throwable

  def error() = throw new MyError("error")

  type Var = String
}